package com.example.screw.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.screw.constants.MaterialCarbonCoefficient;
import com.example.screw.constants.RtnCode;
import com.example.screw.entity.Order;
import com.example.screw.repository.OrderDao;
import com.example.screw.repository.OrderManagementDao;
import com.example.screw.service.ifs.OrderService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationItem;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.OrderManagementRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProduceObj;
import com.example.screw.vo.ScrewMaterialObj;
import com.example.screw.vo.SearchOrderRes;
import com.example.screw.vo.SettleOrderVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderManagementDao orderManagementDao;

	private ObjectMapper mapper = new ObjectMapper();
	
	// �s�W�渹
	@Override
	public BaseRes createOrder(String orderNumber, String name, int aim, int weight, List<ProduceObj> raw, List<ProduceObj> process) {
		// �]�w�C�� processObj ���ұƫY��
		for(ProduceObj processObj : process) {
			processObj.setCarbonCoefficient(MaterialCarbonCoefficient.getCarbonCoefficientByName(processObj.getName()).getCarbonCoefficient());	
		}
		try {
			// �������J�A�p�G�w�s�b�|�^�� 0
			if (orderDao.insertOrder(orderNumber, name, aim, weight, mapper.writeValueAsString(raw),
					mapper.writeValueAsString(process)) == 0) {
				return new BaseRes(RtnCode.ORDER_NUMBER_DUPLICATED.getCode(), RtnCode.ORDER_NUMBER_DUPLICATED.getMessage());
			}
		} catch (JsonProcessingException e) {
			return new BaseRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// �s��渹
	@Override
	public BaseRes editOrder(String orderNumber, String name, int aim, int weight, List<ProduceObj> raw,
			List<ProduceObj> process) {
		// �]�w�C�� processObj ���ұƫY��
		for(ProduceObj processObj : process) {
			processObj.setCarbonCoefficient(MaterialCarbonCoefficient.getCarbonCoefficientByName(processObj.getName()).getCarbonCoefficient());	
		}
		// ������s�A�p�G�䤣��|�^�� 0
		try {
			if (orderDao.updateOrder(orderNumber, name, aim, weight, mapper.writeValueAsString(raw),
					mapper.writeValueAsString(process)) == 0) {
				return new BaseRes(RtnCode.ORDER_NUMBER_NOT_EXISTS.getCode(), RtnCode.ORDER_NUMBER_NOT_EXISTS.getMessage());
			}
		} catch (JsonProcessingException e) {
			return new BaseRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// �R���渹
	@Override
	public BaseRes deleteOrder(String orderNumber) {
		int res = orderDao.deleteOrder(orderNumber);
		if(res == 0) {
			return new BaseRes(RtnCode.ORDER_NUMBER_NOT_EXISTS.getCode(), RtnCode.ORDER_NUMBER_NOT_EXISTS.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}
	
	// �j�M�渹
	@Override
	public SearchOrderRes searchOrder(String orderNumber, String name) {
		if(!StringUtils.hasText(orderNumber)) {
			orderNumber = "";
		}
		if(!StringUtils.hasText(name)) {
			name = "";
		}
		// temp ���s��j�M���^�ǭ�
		List<Order> temp = orderDao.searchOrder(orderNumber, name);
		if(CollectionUtils.isEmpty(temp)) {
			return new SearchOrderRes(RtnCode.NO_DATA.getCode(), RtnCode.NO_DATA.getMessage(), null);
		}
		// resObj �O�n�^�Ǫ���
		List<ScrewMaterialObj> resObj = new ArrayList<>();
		// �N�j�M���^�ǭ� temp �g�L�B�z��A�ഫ�^ resObj (1)��򥻸�� (2)�N String ��^ Obj
		for(Order item : temp) {
			try {
				resObj.add(new ScrewMaterialObj(item.getOrderNumber(), item.getName(), item.getAim(), item.getProduce(), item.getWeight(),
						mapper.readValue(item.getRaw(),
								new TypeReference<List<ProduceObj>>() {
						}),
						mapper.readValue(item.getProcess(),
								new TypeReference<List<ProduceObj>>() {
						})));
			} catch (JsonProcessingException e) {
				return new SearchOrderRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage(), null);
			}
		}
		// �ݭn�^�ǡGRtnCode�BRtnMsg�BList<ScrewMaterialObj>
		return new SearchOrderRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), resObj);
	}
	
	// ����U�渹�C�Ѫ��ֿn�q�שM�Ͳ��q
	// �� �� �� �� �� �P (�~)
	@Scheduled(cron = "0 0 0 * * ?", zone="Asia/Taipei")
	@Override
	public void settleOrderPower() {
		// �ھڦs�b���渹�j�M�A�N��� >= �Q��00:00 �B ��� <= �Q��23:59 �� �q�שMpass�[�`
		List<SettleOrderVo> sumToday = orderDao.sumOrderKWhAndPass(LocalDate.now().minusDays(1).atTime(0, 0, 0),
				LocalDate.now().minusDays(1).atTime(23, 59, 59, 999999999), "%%");
		// �p�G�S���^�ǭȡA��ܤ��饼�}�u
		if (CollectionUtils.isEmpty(sumToday)) {
			return;
		}
		// �p�G���^�ǭȡA��� material ����ƥX�ӭק�
		List<Order> dataToday = orderDao.selectOrderToday(LocalDate.now().minusDays(1).atTime(0, 0, 0),
				LocalDate.now().minusDays(1).atTime(23, 59, 59, 999999999));
		// �N�Ǧ^���ȡA���O�g�J�渹�� produce �M produce.�q�O
		for (int i = 0; i < dataToday.size(); i++) {
			// �]�����K�[ order by�A�G dataToday �M sumToday �����渹���Ǫ֩w�|�@��
			// �N�L�h�����q�M���Ѫ����q�[�`�g�^�`���q
			dataToday.get(i).setProduce((int) (dataToday.get(i).getProduce() + sumToday.get(i).getProduceTodaySum()));
			// �N process �q�r����^�����A�w��ϥΪ��`�q�׭ק�A�A�ন�r��g�J
			try {
				List<ProduceObj> processObjList = mapper.readValue(dataToday.get(i).getProcess(),
						new TypeReference<List<ProduceObj>>() {
						});
				for (ProduceObj obj : processObjList) {
					if (obj.getName().equals("�q�O")) {
						obj.setAmount(Math.round((obj.getAmount() + sumToday.get(i).getKWhSum())*1000)/1000);
						break;
					}
				}
				dataToday.get(i).setProcess(mapper.writeValueAsString(processObjList));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		// ������s����A�N��Ʀs�^��Ʈw��
		orderDao.saveAll(dataToday);
	}

	// �p��ұ�(�Ω�D���e�{): �Ѽ�(null) �Ǧ^(�C���渹���s���B�W�١B�ұƭ�)
	// �ھ� result �� RtnCode �ӧP�_�O�_�N��Ʀs�� cache
	@Override
	public CalculateInformationRes calculateCarbonEmission() {
		// ����Ҧ��渹����T
		List<Order> dataAll = orderDao.searchOrder("", "");
		// �ΨӸ˨��ֿn��� ( �H�渹�@�����ޭȡAList �� : 0 ��ֿn�q�סA1 ��ֿn�Ͳ��q�A2 ��ؼжq�A3 �񭫶q )
		Map<String, List<Double>> oldDataMap = new LinkedHashMap<>();
		// �ΨӸ˨��n�^�Ǫ��p�⵲�G ( �H�渹�@�����ޭȡA���e�O�n�^�Ǫ���� )
		Map<String, CalculateInformationItem> calculateInformationItemMap = new LinkedHashMap<>();
		// �ΨӰO���q�O��index
		Map<String, Integer> indexForPower = new LinkedHashMap<>();
		// ��C�@���渹���B�z�G(1) �N String ��^ Obj (2) �p��ұƩ� (3) �N���e�ȩ�� calculateInformationItemMap ��
		for (Order data : dataAll) {
			try {
				// ��Ʊq String �ഫ�� Obj
				List<ProduceObj> rawList = mapper.readValue(data.getRaw(), new TypeReference<List<ProduceObj>>() {
				});
				// ��ƪ��ұƩ�p��
				double raw = 0;
				for (ProduceObj rawObj : rawList) {
					raw += rawObj.getAmount() * (rawObj.getCarbonCoefficient());
				}
				// �s�{�q String �ഫ�� Obj
				List<ProduceObj> processList = mapper.readValue(data.getProcess(), new TypeReference<List<ProduceObj>>() {
				});
				// �s�{���ұƩ�p��
				double process = 0;
				for (ProduceObj processObj : processList) {
					// �p�G�O�q�O�����i��p��A�����ֿn�q�סB�ֿn�Ͳ��q�B�ؼжq�B���q
					if (processObj.getName().equals("�q�O")) {
						indexForPower.put(data.getOrderNumber(), processList.indexOf(processObj));
						oldDataMap.put(data.getOrderNumber(), new ArrayList<Double>(Arrays.asList(processObj.getAmount(),
								(double) data.getProduce(), (double) data.getAim(), (double) data.getWeight())));
						continue;
					}
					// process ���ұ� = �ϥζq*�ұƩ�Y��/�ӫ~���q(kg)
					process += (double) Math.round((processObj.getAmount() * processObj.getCarbonCoefficient() / (data.getAim() * data.getWeight() / 1000))*10000)/10000;
				}
				// �N�ثe��X�Ӫ����F�q�O�H�~���ұ��`�ȡA��J map �� (CalculateItem=�渹+�W��+�p��X���ұƵ��G+�ؼжq+���q+���q+���+�s�{)
				calculateInformationItemMap.put(data.getOrderNumber(),    // �H�渹�@�� key ��
						new CalculateInformationItem(data.getOrderNumber(), data.getName(), raw + process, data.getAim(), data.getProduce(), data.getWeight(), rawList, processList));
			} catch (JsonProcessingException e) {
				return new CalculateInformationRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage(), null);
			}
		}
		// �q receive_data �N time >= ���� 00:00 �B time <= now ����ƨ̳渹���ե[�`�q�שM���q��̳渹�Ƨǫ�^��
		List<SettleOrderVo> sumRealTime = orderDao.
				sumOrderKWhAndPass(LocalDate.now().atTime(0, 0, 0), LocalDateTime.now(), "%%");
		// �p�G�S�}�u�G�����έ�l�q�q�p��A�A�P��L�ҫY�Ƭۥ[�Y�i�A�åB�� RtnCode �i����ƥi�H�s�b Cache
		if (CollectionUtils.isEmpty(sumRealTime)) {
			for (Entry<String, List<Double>> oldDataEntry : oldDataMap.entrySet()) {
				// �Y�ֿn�Ͳ��q�� 0 �h�������}
				if(oldDataEntry.getValue().get(1) == 0) {
					continue;
				}
				// �S���s��ƪ��q�O�ұ� = (�ֿn�q��/�ֿn���q*�ؼв��q)*�ұƫY��/�`���q(kg)
				// oldDataEntry �H�渹�@�����ޭȡAList �� : 0 ��ֿn�q�סA1 ��ֿn�Ͳ��q�A2 ��ؼжq�A3 �񭫶q
				double powerCarbon = Math.round(((oldDataEntry.getValue().get(0) / oldDataEntry.getValue().get(1) * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("�q�O").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000))*10000)/10000;
				// �N�ӵ��渹���Ҧ���ƨ��X��
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// �]�w���q
				temp.setProduce((int) Math.round(oldDataEntry.getValue().get(1)));
				// �N�q�O���ұƫY�ƭp��n��A�M���]�t�q�O���ұƸ�Ƭۥ[�s�^�h
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// �N�ӵ��渹���Ҧ���Ʀs�^�h
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
			}
			// �N��z��������ƿ�X�� List<calculateItem>
			List<CalculateInformationItem> calculateList = new ArrayList<>();
			for (Entry<String, CalculateInformationItem> eachData : calculateInformationItemMap.entrySet()) {
				calculateList.add(eachData.getValue());
			}
			return new CalculateInformationRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
					calculateList);
		}
		// �p�G���}�u�G�⤵�Ѫ��q�שM���q�[�W�h�A���۰��@�˪��p��
		// sumIndex �N��ثe sumRealTime ��z��ĴX��
		int sumIndex = 0;
		// oldDataEntry �H�渹�@�����ޭȡAList �� : 0 ��ֿn�q�סA1 ��ֿn�Ͳ��q�A2 ��ؼжq�A3 �񭫶q
		for (Entry<String, List<Double>> oldDataEntry : oldDataMap.entrySet()) {
			// �p�G sumIndex �٨S�W�L���סA��ܩ|�����[�J�έp����s��ơA�B�� sumRealTime ���渹�M eachUpdate �� key �ȬۦP�A��ܳo���渹���ݭn�K�[���q��
			if (sumIndex < sumRealTime.size() && sumRealTime.get(sumIndex).getOrderNumber().equals(oldDataEntry.getKey())) {
				// �p���`�q��
				double powerTotal = Math.round((sumRealTime.get(sumIndex).getKWhSum() + oldDataEntry.getValue().get(0))*10000)/10000;
				// �p���`���q
				double produceTotal = sumRealTime.get(sumIndex).getProduceTodaySum() + oldDataEntry.getValue().get(1);
				// �q���ұ� = (�C�����q��*�ؼ�����)*�ұƫY��/�ؼ��`���q
				double powerCarbon = Math.round((( powerTotal / produceTotal * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("�q�O").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000))*10000)/10000;
				// �N�ӵ��渹���Ҧ���ƨ��X��
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// �N�q�O���ұƫY�ƭp��n��A�M���]�t�q�O���ұƸ�Ƭۥ[�s�^�h
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// �]�w���q
				temp.setProduce((int)produceTotal);
				// ����q�O����ơA�]�w amount
				temp.getProcess().get(indexForPower.get(oldDataEntry.getKey())).setAmount(powerTotal);
				// �N�ӵ��渹���Ҧ���Ʀs�^�h
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
				sumIndex++;
			} else {
				// �S���s��ơA�ϥέ쥻����ƶi��@��B��Y�i
				// �Y�ֿn�Ͳ��q�� 0 �h�������}
				if(oldDataEntry.getValue().get(1) == 0) {
					continue;
				}
				// �S���s��ƪ��q�O�ұ� = (�ֿn�q��/�ֿn���q*�ؼв��q)*�ұƫY��/�`���q(kg)
				double powerCarbon =  Math.round(((oldDataEntry.getValue().get(0) / oldDataEntry.getValue().get(1) * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("�q�O").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000))*10000)/10000;						
				// �N�ӵ��渹���Ҧ���ƨ��X��
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// �]�w���q
				temp.setProduce((int) Math.round(oldDataEntry.getValue().get(1)));
				// �N�q�O���ұƫY�ƭp��n��A�M���]�t�q�O���ұƸ�Ƭۥ[�s�^�h
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// �N�ӵ��渹���Ҧ���Ʀs�^�h
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
			}
		}
		// �N��z��������ƿ�X�� List<calculateItem> �^��
		List<CalculateInformationItem> calculateList = new ArrayList<>();
		for (Entry<String, CalculateInformationItem> calculateInformationItem : calculateInformationItemMap.entrySet()) {
			calculateList.add(calculateInformationItem.getValue());
		}
		return new CalculateInformationRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), calculateList);
	}

	@Override
	public OrderManagementRes findOrderAll() {
		
		return new OrderManagementRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), orderManagementDao.getAllOrder());
	}

	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹, �ؼжq, �ثe���q, �������q, �L�h�ֿn�ιq��) �Ǧ^(�̷s�C���������ϥιq��)
	// �Ω�ҲӸ`�e�{�ɩҬd�ݪ���ƨå��Ͳ������ɡA�ݩw���^�ǳ̷s���w���q��
//	@Override
//	public PowerUpdateRes powerUpdate(String orderNumber, int aim, int produce, int weight, double powerUsage) {
//		// �N�o���渹������ֿn�q�ײξ�
//		List<SettleOrderVo> sumRealTime = orderDao
//				.sumOrderKWhAndPass(LocalDate.now().atTime(0, 0, 0), LocalDateTime.now(), orderNumber);
//		// �p���`�q��
//		double powerTotal = powerUsage + sumRealTime.get(0).getKWhSum();
//		// �p���`���q
//		double produceTotal = produce + sumRealTime.get(0).getProduceTodaySum();
//		// �q���ұ� = (�C�����q��*�ؼ�����)*�ұƫY��/�ؼ��`���q
//		double powerCarbon = ( powerTotal / produceTotal * aim)
//				* MaterialCarbonCoefficient.getCarbonCoefficientByName("�q�O").getCarbonCoefficient()
//				/ (aim * weight / 1000);
//		return new PowerUpdateRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), (powerTotal / produceTotal * aim), powerCarbon);
//	}
	
}
