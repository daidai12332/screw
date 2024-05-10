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
	
	// �s�W�渹
	@Override
	public BaseRes addOrder(String orderNumber, String name, int aim, int weight, List<ProduceObj> raw, List<ProduceObj> process) {
		// �]�w�C�� processObj ���ұƫY��
		for(ProduceObj processObj : process) {
			processObj.setCarbonCoefficient(MaterialCarbonCoefficient.getCarbonCoefficientByName(processObj.getName()).getCarbonCoefficient());	
		}
		try {
			// �������J�A�p�G�w�s�b�|�^�� 0
			if (screwMaterialDao.insertScrewMaterial(orderNumber, name, aim, weight, mapper.writeValueAsString(raw),
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
			if (screwMaterialDao.updateScrewMaterial(orderNumber, name, aim, weight, mapper.writeValueAsString(raw),
					mapper.writeValueAsString(process)) == 0) {
				return new BaseRes(RtnCode.ORDER_NUMBER_NOT_EXISTS.getCode(), RtnCode.ORDER_NUMBER_NOT_EXISTS.getMessage());
			}
		} catch (JsonProcessingException e) {
			return new BaseRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// ����U�渹�C�Ѫ��ֿn�q�שM�Ͳ��q
	// �� �� �� �� �� �P (�~)
	@Scheduled(cron = "0 0 0 * * ?", zone="Asia/Taipei")
	@Override
	public void settleOrderPower() {
		// �קאּ�Q�Ѫ����
		String settleDate = LocalDate.now().minusDays(1).toString();
		// �ھڦs�b���渹�j�M�A�N��� >= �Q��00:00 �B ��� <= �Q��23:59 �� �q�שMpass�[�`
		List<SettleOrderVo> sumToday = screwMaterialDao.sumOrderKWhAndPass(settleDate + " 00:00:00",
				settleDate + " 23:59:59", "%%");
		// �p�G�S���^�ǭȡA��ܤ��饼�}�u
		if (CollectionUtils.isEmpty(sumToday)) {
			return;
		}
		// �p�G���^�ǭȡA��� material ����ƥX�ӭק�
		List<ScrewMaterial> dataToday = screwMaterialDao.selectScrewMaterialToday(settleDate + " 00:00:00",
				settleDate + " 23:59:59");
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
					if (obj.getName() == "�q�O") {
						obj.setAmount(obj.getAmount() + sumToday.get(i).getKWhSum());
						break;
					}
				}
				dataToday.get(i).setProcess(mapper.writeValueAsString(processObjList));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		// ������s����A�N��Ʀs�^��Ʈw��
		screwMaterialDao.saveAll(dataToday);
	}

	// �p��ұ�(�Ω�D���e�{): �Ѽ�(null) �Ǧ^(�C���渹���s���B�W�١B�ұƭ�)
	// �ھ� result �� RtnCode �ӧP�_�O�_�N��Ʀs�� cache
	@Override
	public CalculateInformationRes calculateCarbonEmission() {
		// ����Ҧ��渹����T
		List<ScrewMaterial> dataAll = screwMaterialDao.findAll();
		// �ΨӸ˨��ֿn��� ( �H�渹�@�����ޭȡAList �� : 0 ��ֿn�q�סA1 ��ֿn�Ͳ��q�A2 ��ؼжq�A3 �񭫶q )
		Map<String, List<Double>> oldDataMap = new LinkedHashMap<>();
		// �ΨӸ˨��n�^�Ǫ��p�⵲�G ( �H�渹�@�����ޭȡA���e�O�n�^�Ǫ���� )
		Map<String, CalculateInformationItem> calculateInformationItemMap = new LinkedHashMap<>();
		// �P�_�Ҧ��渹�O�_�Ҥw�Ͳ��X�ؼжq�A�Y�O�A�L���i�J��Ʈw�s��
		boolean allDone = true;
		// ��C�@���渹���B�z�G(1) �N String ��^ Obj (2) �p��ұƩ� (3) �N���e�ȩ�� calculateInformationItemMap ��
		for (ScrewMaterial data : dataAll) {
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
				List<ProduceObj> processList = mapper.readValue(data.getRaw(), new TypeReference<List<ProduceObj>>() {
				});
				// �s�{���ұƩ�p��
				double process = 0;
				for (ProduceObj processObj : processList) {
					// �p�G�O�q�O�����i��p��A�����ֿn�q�סB�ֿn�Ͳ��q�B�ؼжq�B���q
					if (processObj.getName() == "�q�O") {
						oldDataMap.put(data.getOrderNumber(), new ArrayList<Double>(Arrays.asList(processObj.getAmount(),
								(double) data.getProduce(), (double) data.getAim(), (double) data.getWeight())));
						continue;
					}
					// process ���ұ� = �ϥζq*�ұƩ�Y��/�ӫ~���q(kg)
					process += (double) processObj.getAmount() * processObj.getCarbonCoefficient() / (data.getAim() * data.getWeight() / 1000);
				}
				// �u�n�����@���q�楼�F��ؼжq�A�N�ݭn�i�J��Ʈw
				if(!allDone || data.getAim() != data.getProduce()) {
					allDone = false;
				}
				// �N�ثe��X�Ӫ����F�q�O�H�~���ұ��`�ȡA��J map �� (CalculateItem=�渹+�W��+�p��X���ұƵ��G+�ؼжq+���q+���q+���+�s�{)
				calculateInformationItemMap.put(data.getOrderNumber(),    // �H�渹�@�� key ��
						new CalculateInformationItem(data.getOrderNumber(), data.getName(), raw + process, data.getAim(), data.getProduce(), data.getWeight(), rawList, processList));
			} catch (JsonProcessingException e) {
				return new CalculateInformationRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage(), null);
			}
		}
		// �]�w�n�j�M���ɶ�
		LocalTime nowTime = LocalTime.now();
		List<SettleOrderVo> sumRealTime = null;
		// �p�G�S�������F��ؼжq�A�N�i�J��Ʈw
		if(!allDone) {
			// �q receive_data �N time >= ���� 00:00 �B time <= now ����ƨ̳渹���ե[�`�q�שM���q��̳渹�Ƨǫ�^��
			sumRealTime = screwMaterialDao
			.sumOrderKWhAndPass(LocalDate.now().toString() + " 00:00:00", nowTime.toString(), "%%");
		}
		// �p�G�S�i�J��Ʈwor�S�}�u�G�����έ�l�q�q�p��A�A�P��L�ҫY�Ƭۥ[�Y�i�A�åB�� RtnCode �i����ƥi�H�s�b Cache
		if (CollectionUtils.isEmpty(sumRealTime)) {
			for (Entry<String, List<Double>> oldDataEntry : oldDataMap.entrySet()) {
				// �S���s��ƪ��q�O�ұ� = (�ֿn�q��/�ֿn���q*�ؼв��q)*�ұƫY��/�`���q(kg)
				// oldDataEntry �H�渹�@�����ޭȡAList �� : 0 ��ֿn�q�סA1 ��ֿn�Ͳ��q�A2 ��ؼжq�A3 �񭫶q
				double powerCarbon = (oldDataEntry.getValue().get(0) / oldDataEntry.getValue().get(1) * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("�q�O").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000);
				// �N�ӵ��渹���Ҧ���ƨ��X��
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// �N�q�O���ұƫY�ƭp��n��A�M���]�t�q�O���ұƸ�Ƭۥ[�s�^�h
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// �N�ӵ��渹���Ҧ���Ʀs�^�h
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
			}
			// �N��z��������ƿ�X�� List<calculateItem>�A�èϥ� RtnCode ����ݤ��ݭn�s�� Cache
			List<CalculateInformationItem> calculateList = new ArrayList<>();
			for (Entry<String, CalculateInformationItem> eachData : calculateInformationItemMap.entrySet()) {
				calculateList.add(eachData.getValue());
			}
			return new CalculateInformationRes(RtnCode.SUCCESS_AND_SAVE.getCode(), RtnCode.SUCCESS_AND_SAVE.getMessage(),
					calculateList);
		}
		// �p�G���}�u�G�⤵�Ѫ��q�שM���q�[�W�h�A���۰��@�˪��p��
		// sumIndex �N��ثe sumRealTime ��z��ĴX��
		int sumIndex = 0;
		// oldDataEntry �H�渹�@�����ޭȡAList �� : 0 ��ֿn�q�סA1 ��ֿn�Ͳ��q�A2 ��ؼжq�A3 �񭫶q
		for (Entry<String, List<Double>> oldDataEntry : oldDataMap.entrySet()) {
			// �p�G sumIndex �٨S�W�L���סA��ܩ|�����[�J�έp����s��ơA�B�� sumRealTime ���渹�M eachUpdate �� key �ȬۦP�A��ܳo���渹���ݭn�K�[���q��
			if (sumIndex < sumRealTime.size() && sumRealTime.get(sumIndex).getOrderNo().equals(oldDataEntry.getKey())) {
				// �p���`�q��
				double powerTotal = sumRealTime.get(sumIndex).getKWhSum() + oldDataEntry.getValue().get(0);
				// �p���`���q
				double produceTotal = sumRealTime.get(sumIndex).getProduceTodaySum() + oldDataEntry.getValue().get(1);
				// �q���ұ� = (�C�����q��*�ؼ�����)*�ұƫY��/�ؼ��`���q
				double powerCarbon = ( powerTotal / produceTotal * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("�q�O").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000);
				// �N�ӵ��渹���Ҧ���ƨ��X��
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
				// �N�q�O���ұƫY�ƭp��n��A�M���]�t�q�O���ұƸ�Ƭۥ[�s�^�h
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				// �N�ӵ��渹���Ҧ���Ʀs�^�h
				calculateInformationItemMap.put(oldDataEntry.getKey(), temp);
				sumIndex++;
			} else {
				// �S���s��ơA�ϥέ쥻����ƶi��@��B��Y�i�G �S���s��ƪ��q�O�ұ� = (�ֿn�q��/�ֿn���q*�ؼв��q)*�ұƫY��/�`���q(kg)
				double powerCarbon = ( oldDataEntry.getValue().get(0) / oldDataEntry.getValue().get(1) * oldDataEntry.getValue().get(2))
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("�q�O").getCarbonCoefficient()
						/ (oldDataEntry.getValue().get(2) * oldDataEntry.getValue().get(3) / 1000);						
				// �N�ӵ��渹���Ҧ���ƨ��X��
				CalculateInformationItem temp = calculateInformationItemMap.get(oldDataEntry.getKey());
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

	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹, �ؼжq, �ثe���q, �������q, �L�h�ֿn�ιq��) �Ǧ^(�̷s�C���������ϥιq��)
	// �Ω�ҲӸ`�e�{�ɩҬd�ݪ���ƨå��Ͳ������ɡA�ݩw���^�ǳ̷s���w���q��
	@Override
	public PowerUpdateRes powerUpdate(String orderNumber, int aim, int produce, int weight, double powerUsage) {
		// �N�o���渹������ֿn�q�ײξ�
		List<SettleOrderVo> sumRealTime = screwMaterialDao
				.sumOrderKWhAndPass(LocalDate.now().toString() + " 00:00:00", LocalTime.now().toString(), orderNumber);
		// �p���`�q��
		double powerTotal = powerUsage + sumRealTime.get(0).getKWhSum();
		// �p���`���q
		double produceTotal = produce + sumRealTime.get(0).getProduceTodaySum();
		// �q���ұ� = (�C�����q��*�ؼ�����)*�ұƫY��/�ؼ��`���q
		double powerCarbon = ( powerTotal / produceTotal * aim)
				* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
				/ (aim * weight / 1000);
		return new PowerUpdateRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), (powerTotal / produceTotal * aim), powerCarbon);
	}
	
}
