package com.example.screw.service.ifs;


import java.time.LocalDate;
import java.util.List;

import com.example.screw.entity.ReceiveData;
import com.example.screw.vo.BaseRes;

import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentHourRes;
import com.example.screw.vo.EquipmentHoursDayRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.OrderAndMachineRes;
import com.example.screw.vo.StatusAndOrderRes;
import com.example.screw.vo.VoltageRes;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MachineService {
	
	// �x�s���x�@�ѥ��������
	public void machineDataDay ();
	
	// ����
	// ���Ҧ����x���̷s���
	public StatusAndOrderRes findmachineDataNow();
	
	// �e�x�S�w���x���
	// ���o�S�w���x�@�g�������U�����
	public EquipmentRes machineDataWeek(String machineName);
	
	// �e�x�S�w���x���
	// ���o���x�@�Ӥ몺�����U�����
	public EquipmentRes machineDataMonth(String machineName);
	
	// �e�x�S�w���x���
	// ���o���x�@�~�������U�����
	public EquipmentRes machineDataYear(String machineName);
	
	
//	//���o�ثe�q��
//	public VoltageRes getVoltage();
	
	// ��x
	//��s�q��
	public BaseRes updateVoltage(double voltage, String machineName);
	
//	// ���o����ֿn�q��
//	public ElectricityRes electricityPeriod();
	
	// ��x
	//���o�{���]�ƪ��W��
	public MachineNameRes findMachineName();
	
	// ��x
	//�R���]��
	public BaseRes deleteMachine(MachineNameReq req);
	
	// ��x
	//�s�W�]��
	public BaseRes addMachine(String machineName);
	
	//�C�p���x�s���x�U�����
	public void machineDataHour();
	
	// ������e�o�Ӥp�ɲֿn���
	//���o�̷s���@�Ӥp�ɤ������
	public EquipmentHourRes machineNewHourData();
	
	// �������e���X�Ӥp�ɲֿn�����
	//���o���餣�P�������x�U�����
	public EquipmentHoursDayRes machineHoursData();
	
	// ���� �渹
	//���o���馳�Ͳ����渹�����q�P���x���U�����
	public OrderAndMachineRes orderDataDay();
}
