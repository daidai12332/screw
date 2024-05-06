package com.example.screw.service.ifs;

import java.util.List;

import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProcessObj;
import com.example.screw.vo.RawObj;

public interface ScrewService {

	// �s�W�渹
	public BaseRes addOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw, List<ProcessObj> process);

	// �s��渹
	public BaseRes editOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw, List<ProcessObj> process);

	// �C�鵲��U�渹�ֿn�ӹq�q�M�Ͳ��q(Schedule)
	public void settleOrderPower();

	// �p��ұ�(�Ω�ұƧe�{): �Ѽ�(null) �Ǧ^(�C���渹����Ʈw��ơB�ұƭ�)
	public CalculateInformationRes calculateCarbonEmission();
	
	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹) �Ǧ^(�̷s�C���������ϥιq��)
	public PowerUpdateRes powerUpdate(int orderNo, int aim, int produce,int weight, double powerUsage);
}
