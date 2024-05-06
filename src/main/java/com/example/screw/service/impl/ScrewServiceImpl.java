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

	// �s�W�渹
	@Override
	public BaseRes addOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw, List<ProcessObj> process) {
		// �������J�A�p�G�w�s�b�|�^�� 0
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

	// �s��渹
	@Override
	public BaseRes editOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw,
			List<ProcessObj> process) {
		// ������s�A�p�G�䤣��|�^�� 0
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

	// �C�鵲��U�渹�ֿn�ӹq�q�M�Ͳ��q(Schedule)
	@Override
	public void settleOrderPower() {
		// �קאּ�Q�Ѫ����
		String settleDate = LocalDate.now().minusDays(1).toString();
		// �ھڦs�b���渹�j�M�A�N��� >= �Q��00:00 �B ��� <= �Q��23:59 �� �q�y�q�[�`/24�p�ɪ���� pass�[�`
		List<SettleOrderVo> sumToday = screwMaterialDao.sumOrderCurrentAndPass(settleDate + " 00:00:00",
				settleDate + " 23:59:59");
		// �N�Ǧ^���ȡA���O�g�J�渹�� produce �M process.electric
		if (CollectionUtils.isEmpty(sumToday)) {
			// �p�G�S���^�ǭȡA��ܤ��饼�}�u
			return;
		}
		// �p�G���^�ǭȡA��� material ����ƥX�ӭק�
		List<ScrewMaterial> dataToday = screwMaterialDao.selectScrewMaterialToday(settleDate + " 00:00:00",
				settleDate + " 23:59:59");
		for (int i = 0; i < dataToday.size(); i++) {
			// �]�����K�[ order by�A�G dataToday �M sumToday �����渹���Ǫ֩w�|�@��
			// �N�L�h�����q�M���Ѫ����q�[�`�g�^�`���q
			dataToday.get(i).setProduce((int) (dataToday.get(i).getProduce() + sumToday.get(i).getProduceTodaySum()));
			// �N process �q�r����^�����A�w��q�q���ϥζq�i��ק�(�L�h���ζq�M���Ѫ�"24�p�ɥ����C��"�ζq�[�`)�A�A�ন�r��g�J
			try {
				List<ProcessObj> processObjList = mapper.readValue(dataToday.get(i).getProcess(),
						new TypeReference<List<ProcessObj>>() {
						});
				for (ProcessObj obj : processObjList) {
					if (obj.getName() == "electric") { /***** �o�̥i��n�ק�A�Τ@�W�r *****/
						obj.setAmount(obj.getAmount() + sumToday.get(i).getCurrentSum() / 86400);
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

	// �p��ɰO�o�n���ثe�Ͳ����q�Ӧ����
	// �p��ұ�(�Ω�D���e�{): �Ѽ�(null) �Ǧ^(�C���渹���s���B�W�١B�ұƭ�)
	// �ھ� result �� RtnCode �ӧP�_�O�_�N��Ʀs�� cache
	@Override
	public CalculateInformationRes calculateCarbonEmission() {
		// �]�w�n�j�M���ɶ�
		LocalTime nowTime = LocalTime.now();
		// �q receive_data �N time >= ���� 00:00 �B time <= now ����ƨ̳渹���ե[�`�q�q�M���q�̳渹�Ƨǫ�^��
		List<SettleOrderVo> sumRealTime = screwMaterialDao
				.sumOrderCurrentAndPass(LocalDate.now().toString() + " 00:00:00", nowTime.toString());
		// ����Ҧ��渹����T
		List<ScrewMaterial> dataAll = screwMaterialDao.findAll();
		// �H�渹�@�����ޭȡA�ΨӸ˸ӳ渹���L�h���q�שM�Ͳ��q�A0��q�סA1��Ͳ��q�A2��ؼжq�A3�񭫶q
		Map<String, List<Double>> updateData = new LinkedHashMap<>();
		// �H�渹�@�����ޭȡA���e�O�n�^�Ǫ�����s���p�⵲�G
		Map<String, CalculateInformationItem> calculateInformationItemMap = new LinkedHashMap<>();
		for (ScrewMaterial data : dataAll) {
			try {
				// �N�C�ӭ�ƪ��ұƩ�[�`
				List<RawObj> rawObj = mapper.readValue(data.getRaw(), new TypeReference<List<RawObj>>() {
				});
				double raw = 0;
				for (RawObj rawItem : rawObj) {
					raw += rawItem.getAmount() * (rawItem.getScopeOne() + rawItem.getScopeTwo());
				}
				// �N�q�O�H�~���ұƩ�[�`
				List<ProcessObj> processObj = mapper.readValue(data.getRaw(), new TypeReference<List<ProcessObj>>() {
				});
				double process = 0;
				for (ProcessObj processItem : processObj) {
					// �p�G�O�q�O�����i��p��
					if (processItem.getName() == "electric") { /****** �i��|�ݭn��W���a�� ***/
						updateData.put(data.getOrderNo(), new ArrayList<Double>(Arrays.asList(processItem.getAmount(),
								(double) data.getProduce(), (double) data.getAim(), (double) data.getWeight())));
						continue;
					}
					// process ���ұ� = �ϥζq*�ұƩ�Y��/�ӫ~���q(kg)
					process += (double) processItem.getAmount() * MaterialCarbonCoefficient
							.getCarbonCoefficientByName(processItem.getName()).getCarbonCoefficient()
							/ (data.getAim() * data.getWeight() / 1000);
				}
				// �N�ثe��X�Ӫ����F�q�O�H�~���ұ��`�ȡA��J map �� (CalculateItem=�渹+�W��+�p��X���ұƵ��G+�ؼжq+���q+���q+���+�s�{)
				calculateInformationItemMap.put(data.getOrderNo(),
						new CalculateInformationItem(data.getOrderNo(), data.getName(), raw + process, data.getAim(), data.getProduce(), data.getWeight(), rawObj, processObj));
			} catch (JsonProcessingException e) {
				return new CalculateInformationRes(RtnCode.JSON_ERROR.getCode(), RtnCode.JSON_ERROR.getMessage(), null);
			}
		}
		// �p�G�S�}�u�G�����έ�l�q�q�p��A�A�P��L�ҫY�Ƭۥ[�Y�i�A�åB�� RtnCode �i����ƥi�H�s�b Cache
		// �O�o�A����q�����������!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (CollectionUtils.isEmpty(sumRealTime)) {
			// �q���ұ� = �`�q��(�ֿn�C�����q�y*�@�Ѫ����*�q��)/�ثe���q*�ؼХͲ��q*�ұƫY��/�ؼ��`���q
			for (Entry<String, List<Double>> eachUpdate : updateData.entrySet()) {
				double powerCarbon = (eachUpdate.getValue().get(0) * 86400 * 220) / eachUpdate.getValue().get(1)
						* eachUpdate.getValue().get(2)
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
						/ (eachUpdate.getValue().get(2) * eachUpdate.getValue().get(3) / 1000);
				CalculateInformationItem temp = calculateInformationItemMap.get(eachUpdate.getKey());
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				calculateInformationItemMap.put(eachUpdate.getKey(), temp);
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
		int sumIndex = 0;
		for (Entry<String, List<Double>> eachUpdate : updateData.entrySet()) {
			// �p�G sumIndex �٨S�W�L���סA��ܩ|�����[�J�έp����s��ơA�B�� sumRealTime ���渹�M eachUpdate �� key
			// �ȬۦP�A��ܳo���渹���ݭn�K�[���q��
			if (sumIndex < sumRealTime.size() && sumRealTime.get(sumIndex).getOrderNo().equals(eachUpdate.getKey())) {
				// �Υثe���`�q��(���Ѫ��q�y�`�M*�q��+�ֿn�C�����q�y*�@�Ѫ����*�q��)�M�`���q�A���⥭���C�������ݭn���Ӫ��q��(�`�q��/�`���q)�A�A���H�ؼжq�A���H�`���q
				double powerTotal = sumRealTime.get(sumIndex).getCurrentSum() + eachUpdate.getValue().get(0) * 86400;
				// �H�渹�@�����ޭȡA�ΨӸ˸ӳ渹���L�h���q�שM�Ͳ��q�A0��q�סA1��Ͳ��q�A2��ؼжq�A3�񭫶q
				double produceTotal = sumRealTime.get(sumIndex).getProduceTodaySum() + eachUpdate.getValue().get(1);
				// �q���ұ� = �`�q��(�ֿn�C�����q�y*�@�Ѫ����*�q��)/�ثe���q*�ؼХͲ��q*�ұƫY��/�ؼ��`���q
				double powerCarbon = powerTotal * 220 / produceTotal * eachUpdate.getValue().get(2)
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
						/ (eachUpdate.getValue().get(2) * eachUpdate.getValue().get(3) / 1000);
				// �N�p��n���ȻP����s�e���ұƩ�ۥ[�A�]�w�^�h
				CalculateInformationItem temp = calculateInformationItemMap.get(eachUpdate.getKey());
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				calculateInformationItemMap.put(eachUpdate.getKey(), temp);
				sumIndex++;
			} else {
				// �S���s��ơA�ϥέ쥻����ƶi��@��B��Y�i
				double powerCarbon = (eachUpdate.getValue().get(0) * 86400 * 220) / eachUpdate.getValue().get(1)
						* eachUpdate.getValue().get(2)
						* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient()
						/ (eachUpdate.getValue().get(2) * eachUpdate.getValue().get(3) / 1000);
				CalculateInformationItem temp = calculateInformationItemMap.get(eachUpdate.getKey());
				temp.setCarbonEmission(temp.getCarbonEmission() + powerCarbon);
				calculateInformationItemMap.put(eachUpdate.getKey(), temp);
			}
		}
		// �N��z��������ƿ�X�� List<calculateItem> �^��
		List<CalculateInformationItem> calculateList = new ArrayList<>();
		for (Entry<String, CalculateInformationItem> eachData : calculateInformationItemMap.entrySet()) {
			calculateList.add(eachData.getValue());
		}
		return new CalculateInformationRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), calculateList);
	}

	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹) �Ǧ^(�̷s�ֿn�q�שM�ϥζq)
	@Override
	public PowerUpdateRes powerUpdate(int orderNo, int aim, int produce,int weight, double powerUsage) {
		// �Ω�ҲӸ`�e�{�ɩҬd�ݪ���ƨå��Ͳ������ɡA�ݩw���^�ǳ̷s���w���q��
		// �]�w�n�j�M���ɶ�
		LocalTime nowTime = LocalTime.now();
		// �N�o���渹���ұƩ�ξ�
		SettleOrderVo sumRealTime = screwMaterialDao
				.sumParticularOrderCurrentAndPass(LocalDate.now().toString() + " 00:00:00", nowTime.toString(), orderNo);
		// �Υثe���`�q��(���Ѫ��q�y�`�M*�q��+�ֿn�C�����q�y*�@�Ѫ����*�q��)�M�`���q�A���⥭���C�������ݭn���Ӫ��q��(�`�q��/�`���q)�A�A���H�ؼжq�A���H�`���q
		double powerTotal = sumRealTime.getCurrentSum() + powerUsage * 86400 ;
		double produceTotal = sumRealTime.getProduceTodaySum() + produce;
		// �q���ұ� = �`�q��(�ֿn�C�����q�y*�@�Ѫ����*�q��)/�ثe���q*�ؼХͲ��q*�ұƫY��/�ؼ��`���q
		double powerUsageEstimate = powerTotal * 220 / produceTotal * aim;
		double powerCarbon = powerUsageEstimate	* MaterialCarbonCoefficient.getCarbonCoefficientByName("electric").getCarbonCoefficient() / (aim * weight / 1000);				
		// �w���q�ת��p��覡�P�W
		return new PowerUpdateRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), powerUsageEstimate, powerCarbon);
	}

}
