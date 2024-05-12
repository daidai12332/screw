package com.example.screw.service.ifs;

import java.time.LocalDate;
import java.util.List;

import com.example.screw.entity.ReceiveData;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.StatusAndOrderRes;
import com.example.screw.vo.VoltageRes;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MachineService {
	
	// �x�s���x�@�ѥ��������
	public void machineDataDay ();
	
	// ���Ҧ����x���̷s���
	public StatusAndOrderRes findmachineDataNow();
	
	// ���o���x�@�g�������U�����
	public EquipmentRes machineDataWeek(String machineName);
	
	// ���o���x�@�Ӥ몺�����U�����
	public EquipmentRes machineDataMonth(String machineName);
	
	// ���o���x�@�~�������U�����
	public EquipmentRes machineDataYear(String machineName);
	
	//���o�ثe�q��
	public VoltageRes getVoltage();
	
	//��s�q��
	public BaseRes updateVoltage(double voltage);
	
	// ���o����ֿn�q��
	public ElectricityRes electricityPeriod(double voltage);
	
	//���o�{���]�ƪ��W��
	public MachineNameRes findMachineName();
	
	//�R���]��
	public BaseRes deleteMachine(MachineNameReq req);
	
	//�s�W�]��
	public BaseRes addMachine(String machineName);
	
}
