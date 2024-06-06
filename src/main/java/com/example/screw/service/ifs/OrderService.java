package com.example.screw.service.ifs;

import java.util.List;

import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.OrderManagementRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProduceObj;
import com.example.screw.vo.SearchOrderRes;

public interface OrderService {

	// �s�W�渹
	public BaseRes createOrder(String orderNumber, int aim, int weight, int startProcessIndex, int endProcessIndex, String pullThread, String forming, String grindTeeth, String heatTreatment, String electroplating);

	// �s��渹
	public BaseRes editOrder(String orderNumber, int aim, int weight, int startProcessIndex, int endProcessIndex, String pullThread, String forming, String grindTeeth, String heatTreatment, String electroplating);

	// �R���渹
	public BaseRes deleteOrder(String orderNumber);
	
	// ���o��Ʈw���Ҧ��渹���s�y��T
	public SearchOrderRes getOrderManufactureByOrderNumber(String orderNumber);

	// �C�鵲��U�渹�ֿn�ӹq�q�M�Ͳ��q(Schedule)
//	public void settleOrderPower();

	// �p��ұ�(�Ω�ұƧe�{): �Ѽ�(null) �Ǧ^(�C���渹����Ʈw��ơB�ұƭ�)
//	public CalculateInformationRes calculateCarbonEmission();
	
	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹, �ؼжq, �ثe���q, �������q, �L�h�ֿn�ιq��) �Ǧ^(�̷s�C���������ϥιq��)
//	public PowerUpdateRes powerUpdate(String orderNumber, int aim, int produce, int weight, double powerUsage);
	
	//���o��Ʈw���Ҧ��渹
	public OrderManagementRes findOrderAll();
}
