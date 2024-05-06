package com.example.screw.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.screw.constants.MaterialCarbonCoefficient;
import com.example.screw.constants.RtnCode;
import com.example.screw.entity.ScrewMaterial;
import com.example.screw.repository.ScrewMaterialDao;
import com.example.screw.service.ifs.ScrewService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationItem;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProcessObj;
import com.example.screw.vo.RawObj;
import com.example.screw.vo.SettleOrderVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class ScrewServiceImpl implements ScrewService {

	@Autowired
	private ScrewMaterialDao screwMaterialDao;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private ObjectMapper mapper = new ObjectMapper();

	// 新增單號
	@Override
	public BaseRes addOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw, List<ProcessObj> process) {
		// 直接插入，如果已存在會回傳 0
		try {
			if (screwMaterialDao.insertScrewMaterial(orderNo, name, aim, weight, mapper.writeValueAsString(raw),
					mapper.writeValueAsString(process)) == 0) {
				return new BaseRes(RtnCode.ORDER_NO_DUPLICATED.getCode(), RtnCode.ORDER_NO_DUPLICATED.getMessage());
			}
		} catch (JsonProcessingException e) {
			return new BaseRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// 編輯單號
	@Override
	public BaseRes editOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw,
			List<ProcessObj> process) {
		// 直接更新，如果找不到會回傳 0
		try {
			if (screwMaterialDao.updateScrewMaterial(orderNo, name, aim, weight, mapper.writeValueAsString(raw),
					mapper.writeValueAsString(process)) == 0) {
				return new BaseRes(RtnCode.ORDER_NO_NOT_EXISTS.getCode(), RtnCode.ORDER_NO_NOT_EXISTS.getMessage());
			}
		} catch (JsonProcessingException e) {
			return new BaseRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// 每日結算各單號累積耗電量和生產量(Schedule)
	@Override
	public void settleOrderPower() {
		// 修改為昨天的日期
		String settleDate = LocalDate.now().minusDays(1).toString();
		// 根據存在的單號搜尋，將日期 >= 昨天00:00 且 日期 <= 昨天23:59 的 電流量加總/24小時的秒數 pass加總
		List<SettleOrderVo> sumToday = screwMaterialDao.sumOrderCurrentAndPass(settleDate + " 00:00:00",
				settleDate + " 23:59:59");
		// 將傳回的值，分別寫入單號的 produce 和 process.electric
		if (CollectionUtils.isEmpty(sumToday)) {
			// 如果沒有回傳值，表示今日未開工
			return;
		}
		// 如果有回傳值，抓取 material 的資料出來修改
		List<ScrewMaterial> dataToday = screwMaterialDao.selectScrewMaterialToday(settleDate + " 00:00:00",
				settleDate + " 23:59:59");
		for (int i = 0; i < dataToday.size(); i++) {
			// 因為有添加 order by，故 dataToday 和 sumToday 中的單號順序肯定會一樣
			// 將過去的產量和今天的產量加總寫回總產量
			dataToday.get(i).setProduce((int) (dataToday.get(i).getProduce() + sumToday.get(i).getProduceTodaySum()));
			// 將 process 從字串轉回物件後，針對電量的使用量進行修改(過去的用量和今天的"24小時平均每秒"用量加總)，再轉成字串寫入
			try {
				List<ProcessObj> processObjList = mapper.readValue(dataToday.get(i).getProcess(),
						new TypeReference<List<ProcessObj>>() {
						});
				for (ProcessObj obj : processObjList) {
					if (obj.getName() == "electric") { /***** 這裡可能要修改，統一名字 *****/
						obj.setAmount(obj.getAmount() + sumToday.get(i).getCurrentSum() / 86400);
						break;
					}
				}
				dataToday.get(i).setProcess(mapper.writeValueAsString(processObjList));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		// 全部更新完後，將資料存回資料庫中
		screwMaterialDao.saveAll(dataToday);
	}

	// 計算時記得要拿目前生產的量來估算喔
	// 計算碳排(用於主頁呈現): 參數(null) 傳回(每筆單號的編號、名稱、碳排值)
	// 根據 result 的 RtnCode 來判斷是否將資料存到 cache
	@Override
	public CalculateInformationRes calculateCarbonEmission() {
		// 設定要搜尋的時間
		LocalTime nowTime = LocalTime.now();
		// 從 receive_data 將 time >= 今天 00:00 且 time <= now 的資料依單號分組加總電量和產量依單號排序後回傳
		List<SettleOrderVo> sumRealTime = screwMaterialDao
				.sumOrderCurrentAndPass(LocalDate.now().toString() + " 00:00:00", nowTime.toString());
		// 抓取所有單號的資訊
		List<ScrewMaterial> dataAll = screwMaterialDao.findAll();
		// 以單號作為索引值，用來裝該單號的過去的電度和生產量，0放電度，1放生產量，2放目標量，3放重量
		Map<String, List<Double>> updateData = new LinkedHashMap<>();
		// 以單號作為索引值，內容是要回傳的未更新的計算結果
		Map<String, CalculateInformationItem> calculateInformationItemMap = new LinkedHashMap<>();
		for (ScrewMaterial data : dataAll) {
			try {
				// 將每個原料的碳排放加總
				List<RawObj> rawObj = mapper.readValue(data.getRaw(), new TypeReference<List<RawObj>>() {
				});
				double raw = 0;
				for (RawObj rawItem : rawObj) {
					raw += rawItem.getAmount() * (rawItem.getScopeOne() + rawItem.getScopeTwo());
				}
				// 將電力以外的碳排放加總
				List<ProcessObj> processObj = mapper.readValue(data.getRaw(), new TypeReference<List<ProcessObj>>() {
				});
				double process = 0;
				for (ProcessObj processItem : processObj) {
					// 如果是電力先不進行計算
					if (processItem.getName() == "electric") { /****** 可能會需要改名的地方 ***/
						updateData.put(data.getOrderNo(), new ArrayList<Double>(Arrays.asList(processItem.getAmount(),
								(double) data.getProduce(), (double) data.getAim(), (double) data.getWeight())));
						continue;
					}
					// process 的碳排 = 使用量*碳排放係數/商品重量(kg)
					process += (double) processItem.getAmount() * MaterialCarbonCoefficient
							.getCarbonCoefficientByName(processItem.getName()).getCarbonCoefficient()
							/ (data.getAim() * data.getWeight() / 1000);
				}
				// 將目前算出來的除了電力以外的碳排總值，放入 map 中 (CalculateItem=單號+名稱+計算出的碳排結果+目標量+產量+重量+原料+製程)
				calculateInformationItemMap.put(data.getOrderNo(),
						new CalculateInformationItem(data.getOrderNo(), data.getName(), raw + process, data.getAim(), data.getProduce(), data.getWeight(), rawObj, processObj));
			} catch (JsonProcessingException e) {
				return new CalculateInformationRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage(), null);
			}
		}
		// 如果沒開工：直接用原始電量計算，再與其他碳係數相加即可，並且用 RtnCode 告知資料可以存在 Cache
		// 記得再抓取電壓喔喔喔喔喔喔喔喔!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (CollectionUtils.isEmpty(sumRealTime)) {
			// 電的碳排 = 總電度(累積每秒平均電流*一天的秒數*電壓)/目前產量*目標生產量*碳排係數/目標總重量
			for (Entry<String, List<Double>> eachUpdate : updateData.entrySet()) {
				double powerCarbon = (eachUpdate.getValue().get(0) * 86400 * 220) / eachUpdate.getValue().get(1)
						* eachUpdate.getValue().get(2)
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
						/ (eachUpdate.getValue().get(2) * eachUpdate.getValue().get(3) / 1000);
				CalculateInformationItem temp = calculateInformationItemMap.get(eachUpdate.getKey());
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				calculateInformationItemMap.put(eachUpdate.getKey(), temp);
			}
			// 將整理完畢的資料輸出成 List<calculateItem>，並使用 RtnCode 分辨需不需要存到 Cache
			List<CalculateInformationItem> calculateList = new ArrayList<>();
			for (Entry<String, CalculateInformationItem> eachData : calculateInformationItemMap.entrySet()) {
				calculateList.add(eachData.getValue());
			}
			return new CalculateInformationRes(RtnCode.SUCCESS_AND_SAVE.getCode(), RtnCode.SUCCESS_AND_SAVE.getMessage(),
					calculateList);
		}
		// 如果有開工：把今天的電度和產量加上去，接著做一樣的計算
		int sumIndex = 0;
		for (Entry<String, List<Double>> eachUpdate : updateData.entrySet()) {
			// 如果 sumIndex 還沒超過長度，表示尚有未加入統計的更新資料，且當 sumRealTime 的單號和 eachUpdate 的 key
			// 值相同，表示這筆單號有需要添加的電度
			if (sumIndex < sumRealTime.size() && sumRealTime.get(sumIndex).getOrderNo().equals(eachUpdate.getKey())) {
				// 用目前的總電度(今天的電流總和*電壓+累積每秒平均電流*一天的秒數*電壓)和總產量，估算平均每顆螺絲需要消耗的電度(總電度/總產量)，再乘以目標量，除以總重量
				double powerTotal = sumRealTime.get(sumIndex).getCurrentSum() + eachUpdate.getValue().get(0) * 86400;
				// 以單號作為索引值，用來裝該單號的過去的電度和生產量，0放電度，1放生產量，2放目標量，3放重量
				double produceTotal = sumRealTime.get(sumIndex).getProduceTodaySum() + eachUpdate.getValue().get(1);
				// 電的碳排 = 總電度(累積每秒平均電流*一天的秒數*電壓)/目前產量*目標生產量*碳排係數/目標總重量
				double powerCarbon = powerTotal * 220 / produceTotal * eachUpdate.getValue().get(2)
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
						/ (eachUpdate.getValue().get(2) * eachUpdate.getValue().get(3) / 1000);
				// 將計算好的值與未更新前的碳排放相加，設定回去
				CalculateInformationItem temp = calculateInformationItemMap.get(eachUpdate.getKey());
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				calculateInformationItemMap.put(eachUpdate.getKey(), temp);
				sumIndex++;
			} else {
				// 沒有新資料，使用原本的資料進行一般運算即可
				double powerCarbon = (eachUpdate.getValue().get(0) * 86400 * 220) / eachUpdate.getValue().get(1)
						* eachUpdate.getValue().get(2)
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
						/ (eachUpdate.getValue().get(2) * eachUpdate.getValue().get(3) / 1000);
				CalculateInformationItem temp = calculateInformationItemMap.get(eachUpdate.getKey());
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				calculateInformationItemMap.put(eachUpdate.getKey(), temp);
			}
		}
		// 將整理完畢的資料輸出成 List<calculateItem> 回傳
		List<CalculateInformationItem> calculateList = new ArrayList<>();
		for (Entry<String, CalculateInformationItem> eachData : calculateInformationItemMap.entrySet()) {
			calculateList.add(eachData.getValue());
		}
		return new CalculateInformationRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), calculateList);
	}

	// 更新電力使用量(用於碳排細節呈現): 參數(正在查看的單號) 傳回(最新累積電度和使用量)
	@Override
	public PowerUpdateRes powerUpdate(int orderNo, int aim, int produce,int weight, double powerUsage) {
		// 用於碳細節呈現時所查看的資料並未生產完畢時，需定期回傳最新的預估電度
		// 設定要搜尋的時間
		LocalTime nowTime = LocalTime.now();
		// 將這筆單號的碳排放統整
		SettleOrderVo sumRealTime = screwMaterialDao
				.sumParticularOrderCurrentAndPass(LocalDate.now().toString() + " 00:00:00", nowTime.toString(), orderNo);
		// 用目前的總電度(今天的電流總和*電壓+累積每秒平均電流*一天的秒數*電壓)和總產量，估算平均每顆螺絲需要消耗的電度(總電度/總產量)，再乘以目標量，除以總重量
		double powerTotal = sumRealTime.getCurrentSum() + powerUsage * 86400 ;
		double produceTotal = sumRealTime.getProduceTodaySum() + produce;
		// 電的碳排 = 總電度(累積每秒平均電流*一天的秒數*電壓)/目前產量*目標生產量*碳排係數/目標總重量
		double powerUsageEstimate = powerTotal * 220 / produceTotal * aim;
		double powerCarbon = powerUsageEstimate	* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient() / (aim * weight / 1000);				
		// 預估電度的計算方式同上
		return new PowerUpdateRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), powerUsageEstimate, powerCarbon);
	}

}
