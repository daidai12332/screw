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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.screw.constants.MaterialCarbonCoefficient;
import com.example.screw.constants.RtnCode;
import com.example.screw.entity.ScrewMaterial;
import com.example.screw.repository.ScrewMaterialDao;
import com.example.screw.service.ifs.ScrewMaterialService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationItem;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProduceObj;
import com.example.screw.vo.SettleOrderVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class ScrewMaterialServiceImpl implements ScrewMaterialService {

	@Autowired
	private ScrewMaterialDao screwMaterialDao;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private ObjectMapper mapper = new ObjectMapper();
	
	// 新增單號
	@Override
	public BaseRes addOrder(String orderNumber, String name, int aim, int weight, List<ProduceObj> raw, List<ProduceObj> process) {
		// 設定每筆 processObj 的碳排係數
		for(ProduceObj processObj : process) {
			processObj.setCarbonCoefficient(MaterialCarbonCoefficient.getCarbonCoefficientByName(processObj.getName()).getCarbonCoefficient());	
		}
		try {
			// 直接插入，如果已存在會回傳 0
			if (screwMaterialDao.insertScrewMaterial(orderNumber, name, aim, weight, mapper.writeValueAsString(raw),
					mapper.writeValueAsString(process)) == 0) {
				return new BaseRes(RtnCode.ORDER_NUMBER_DUPLICATED.getCode(), RtnCode.ORDER_NUMBER_DUPLICATED.getMessage());
			}
		} catch (JsonProcessingException e) {
			return new BaseRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// 編輯單號
	@Override
	public BaseRes editOrder(String orderNumber, String name, int aim, int weight, List<ProduceObj> raw,
			List<ProduceObj> process) {
		// 設定每筆 processObj 的碳排係數
		for(ProduceObj processObj : process) {
			processObj.setCarbonCoefficient(MaterialCarbonCoefficient.getCarbonCoefficientByName(processObj.getName()).getCarbonCoefficient());	
		}
		// 直接更新，如果找不到會回傳 0
		try {
			if (screwMaterialDao.updateScrewMaterial(orderNumber, name, aim, weight, mapper.writeValueAsString(raw),
					mapper.writeValueAsString(process)) == 0) {
				return new BaseRes(RtnCode.ORDER_NUMBER_NOT_EXISTS.getCode(), RtnCode.ORDER_NUMBER_NOT_EXISTS.getMessage());
			}
		} catch (JsonProcessingException e) {
			return new BaseRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// 結算各單號每天的累積電度和生產量
	// 秒 分 時 日 月 周 (年)
	@Scheduled(cron = "0 0 0 * * ?", zone="Asia/Taipei")
	@Override
	public void settleOrderPower() {
		// 修改為昨天的日期
		String settleDate = LocalDate.now().minusDays(1).toString();
		// 根據存在的單號搜尋，將日期 >= 昨天00:00 且 日期 <= 昨天23:59 的 電度和pass加總
		List<SettleOrderVo> sumToday = screwMaterialDao.sumOrderKWhAndPass(settleDate + " 00:00:00",
				settleDate + " 23:59:59", "%%");
		// 如果沒有回傳值，表示今日未開工
		if (CollectionUtils.isEmpty(sumToday)) {
			return;
		}
		// 如果有回傳值，抓取 material 的資料出來修改
		List<ScrewMaterial> dataToday = screwMaterialDao.selectScrewMaterialToday(settleDate + " 00:00:00",
				settleDate + " 23:59:59");
		// 將傳回的值，分別寫入單號的 produce 和 produce.電力
		for (int i = 0; i < dataToday.size(); i++) {
			// 因為有添加 order by，故 dataToday 和 sumToday 中的單號順序肯定會一樣
			// 將過去的產量和今天的產量加總寫回總產量
			dataToday.get(i).setProduce((int) (dataToday.get(i).getProduce() + sumToday.get(i).getProduceTodaySum()));
			// 將 process 從字串轉回物件後，針對使用的總電度修改，再轉成字串寫入
			try {
				List<ProduceObj> processObjList = mapper.readValue(dataToday.get(i).getProcess(),
						new TypeReference<List<ProduceObj>>() {
						});
				for (ProduceObj obj : processObjList) {
					if (obj.getName() == "電力") {
						obj.setAmount(obj.getAmount() + sumToday.get(i).getKWhSum());
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

	// 計算碳排(用於主頁呈現): 參數(null) 傳回(每筆單號的編號、名稱、碳排值)
	// 根據 result 的 RtnCode 來判斷是否將資料存到 cache
	@Override
	public CalculateInformationRes calculateCarbonEmission() {
		// 抓取所有單號的資訊
		List<ScrewMaterial> dataAll = screwMaterialDao.findAll();
		// 用來裝取累積資料 ( 以單號作為索引值，List 中 : 0 放累積電度，1 放累積生產量，2 放目標量，3 放重量 )
		Map<String, List<Double>> oldDataMap = new LinkedHashMap<>();
		// 用來裝取要回傳的計算結果 ( 以單號作為索引值，內容是要回傳的資料 )
		Map<String, CalculateInformationItem> calculateInformationItemMap = new LinkedHashMap<>();
		// 判斷所有單號是否皆已生產出目標量，若是，無須進入資料庫存取
		boolean allDone = true;
		// 對每一筆單號做處理：(1) 將 String 轉回 Obj (2) 計算碳排放 (3) 將內容值放到 calculateInformationItemMap 中
		for (ScrewMaterial data : dataAll) {
			try {
				// 原料從 String 轉換為 Obj
				List<ProduceObj> rawList = mapper.readValue(data.getRaw(), new TypeReference<List<ProduceObj>>() {
				});
				// 原料的碳排放計算
				double raw = 0;
				for (ProduceObj rawObj : rawList) {
					raw += rawObj.getAmount() * (rawObj.getCarbonCoefficient());
				}
				// 製程從 String 轉換為 Obj
				List<ProduceObj> processList = mapper.readValue(data.getRaw(), new TypeReference<List<ProduceObj>>() {
				});
				// 製程的碳排放計算
				double process = 0;
				for (ProduceObj processObj : processList) {
					// 如果是電力先不進行計算，紀錄累積電度、累積生產量、目標量、重量
					if (processObj.getName() == "電力") {
						oldDataMap.put(data.getOrderNumber(), new ArrayList<Double>(Arrays.asList(processObj.getAmount(),
								(double) data.getProduce(), (double) data.getAim(), (double) data.getWeight())));
						continue;
					}
					// process 的碳排 = 使用量*碳排放係數/商品重量(kg)
					process += (double) processObj.getAmount() * processObj.getCarbonCoefficient() / (data.getAim() * data.getWeight() / 1000);
				}
				// 只要有任一筆訂單未達到目標量，就需要進入資料庫
				if(!allDone || data.getAim() != data.getProduce()) {
					allDone = false;
				}
				// 將目前算出來的除了電力以外的碳排總值，放入 map 中 (CalculateItem=單號+名稱+計算出的碳排結果+目標量+產量+重量+原料+製程)
				calculateInformationItemMap.put(data.getOrderNumber(),    // 以單號作為 key 值
						new CalculateInformationItem(data.getOrderNumber(), data.getName(), raw + process, data.getAim(), data.getProduce(), data.getWeight(), rawList, processList));
			} catch (JsonProcessingException e) {
				return new CalculateInformationRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage(), null);
			}
		}
		// 設定要搜尋的時間
		LocalTime nowTime = LocalTime.now();
		List<SettleOrderVo> sumRealTime = null;
		// 如果沒有全部達到目標量，就進入資料庫
		if(!allDone) {
			// 從 receive_data 將 time >= 今天 00:00 且 time <= now 的資料依單號分組加總電度和產量後依單號排序後回傳
			sumRealTime = screwMaterialDao
			.sumOrderKWhAndPass(LocalDate.now().toString() + " 00:00:00", nowTime.toString(), "%%");
		}
		// 如果沒進入資料庫or沒開工：直接用原始電量計算，再與其他碳係數相加即可，並且用 RtnCode 告知資料可以存在 Cache
		if (CollectionUtils.isEmpty(sumRealTime)) {
			for (Entry<String, List<Double>> oldDataEntry : oldDataMap.entrySet()) {
				// 沒有新資料的電力碳排 = (累積電度/累積產量*目標產量)*碳排係數/總重量(kg)
				// oldDataEntry 以單號作為索引值，List 中 : 0 放累積電度，1 放累積生產量，2 放目標量，3 放重量
				double powerCarbon = (oldDataEntry.getValue().get(0) / oldDataEntry.getValue().get(1) * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("電力").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000);
				// 將該筆單號的所有資料取出來
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// 將電力的碳排係數計算好後，和未包含電力的碳排資料相加存回去
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// 將該筆單號的所有資料存回去
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
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
		// sumIndex 代表目前 sumRealTime 整理到第幾筆
		int sumIndex = 0;
		// oldDataEntry 以單號作為索引值，List 中 : 0 放累積電度，1 放累積生產量，2 放目標量，3 放重量
		for (Entry<String, List<Double>> oldDataEntry : oldDataMap.entrySet()) {
			// 如果 sumIndex 還沒超過長度，表示尚有未加入統計的更新資料，且當 sumRealTime 的單號和 eachUpdate 的 key 值相同，表示這筆單號有需要添加的電度
			if (sumIndex < sumRealTime.size() && sumRealTime.get(sumIndex).getOrderNo().equals(oldDataEntry.getKey())) {
				// 計算總電度
				double powerTotal = sumRealTime.get(sumIndex).getKWhSum() + oldDataEntry.getValue().get(0);
				// 計算總產量
				double produceTotal = sumRealTime.get(sumIndex).getProduceTodaySum() + oldDataEntry.getValue().get(1);
				// 電的碳排 = (每顆的電度*目標顆數)*碳排係數/目標總重量
				double powerCarbon = ( powerTotal / produceTotal * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("電力").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000);
				// 將該筆單號的所有資料取出來
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// 將電力的碳排係數計算好後，和未包含電力的碳排資料相加存回去
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// 將該筆單號的所有資料存回去
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
				sumIndex++;
			} else {
				// 沒有新資料，使用原本的資料進行一般運算即可： 沒有新資料的電力碳排 = (累積電度/累積產量*目標產量)*碳排係數/總重量(kg)
				double powerCarbon = ( oldDataEntry.getValue().get(0) / oldDataEntry.getValue().get(1) * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("電力").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000);						
				// 將該筆單號的所有資料取出來
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// 將電力的碳排係數計算好後，和未包含電力的碳排資料相加存回去
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// 將該筆單號的所有資料存回去
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
			}
		}
		// 將整理完畢的資料輸出成 List<calculateItem> 回傳
		List<CalculateInformationItem> calculateList = new ArrayList<>();
		for (Entry<String, CalculateInformationItem> calculateInformationItem : calculateInformationItemMap.entrySet()) {
			calculateList.add(calculateInformationItem.getValue());
		}
		return new CalculateInformationRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), calculateList);
	}

	// 更新電力使用量(用於碳排細節呈現): 參數(正在查看的單號, 目標量, 目前產量, 單顆重量, 過去累積用電度) 傳回(最新每顆螺絲的使用電度)
	// 用於碳細節呈現時所查看的資料並未生產完畢時，需定期回傳最新的預估電度
	@Override
	public PowerUpdateRes powerUpdate(String orderNumber, int aim, int produce, int weight, double powerUsage) {
		// 將這筆單號的今日累積電度統整
		List<SettleOrderVo> sumRealTime = screwMaterialDao
				.sumOrderKWhAndPass(LocalDate.now().toString() + " 00:00:00", LocalTime.now().toString(), orderNumber);
		// 計算總電度
		double powerTotal = powerUsage + sumRealTime.get(0).getKWhSum();
		// 計算總產量
		double produceTotal = produce + sumRealTime.get(0).getProduceTodaySum();
		// 電的碳排 = (每顆的電度*目標顆數)*碳排係數/目標總重量
		double powerCarbon = ( powerTotal / produceTotal * aim)
				* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
				/ (aim * weight / 1000);
		return new PowerUpdateRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), (powerTotal / produceTotal * aim), powerCarbon);
	}
	
}
