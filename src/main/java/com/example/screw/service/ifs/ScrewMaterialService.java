package com.example.screw.service.ifs;

import java.util.List;

import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProduceObj;

public interface ScrewMaterialService {

	// �s�W�渹
	public BaseRes addOrder(String orderNumber, String name, int aim, int weight, List<ProduceObj> raw, List<ProduceObj> process);

	// �s��渹
	public BaseRes editOrder(String orderNumber, String name, int aim, int weight, List<ProduceObj> raw, List<ProduceObj> process);

	// �C�鵲��U�渹�ֿn�ӹq�q�M�Ͳ��q(Schedule)
	public void settleOrderPower();

	// �p��ұ�(�Ω�ұƧe�{): �Ѽ�(null) �Ǧ^(�C���渹����Ʈw��ơB�ұƭ�)
	public CalculateInformationRes calculateCarbonEmission();
	
	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹, �ؼжq, �ثe���q, �������q, �L�h�ֿn�ιq��) �Ǧ^(�̷s�C���������ϥιq��)
	public PowerUpdateRes powerUpdate(String orderNumber, int aim, int produce, int weight, double powerUsage);
}