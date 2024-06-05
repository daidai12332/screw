package com.example.screw.service.ifs;


import java.time.LocalDate;
import java.util.List;

import com.example.screw.entity.ReceiveData;
import com.example.screw.vo.BaseRes;

import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentHourRes;
import com.example.screw.vo.EquipmentHoursDayRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.EquipmentWeekRes;
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.MaintenanceRes;
import com.example.screw.vo.OrderAndMachineRes;
import com.example.screw.vo.RecordDeleteReq;
import com.example.screw.vo.RecordReq;
import com.example.screw.vo.RecordStatisticsRes;
import com.example.screw.vo.StatusAndOrderRes;
import com.example.screw.vo.UpdateEquipmentReq;
import com.example.screw.vo.VoltageRes;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MachineService {
	
//	// �x�s���x�@�ѥ��������
//	public void machineDataDay ();
	
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
	//��s���x��T
	public BaseRes updateMachine(UpdateEquipmentReq req);
	
//	// ���o����ֿn�q��
//	public ElectricityRes electricityPeriod();
	
	// ��x
	//���o�{���]�ƪ��U�����
	public MachineNameRes findMachines();
	
	// ��x
	//�R���]��
	public BaseRes deleteMachine(MachineNameReq req);
	
	// ��x
	//�s�W�]��
	public BaseRes addMachine(UpdateEquipmentReq req);
	
	//�C�p���x�s���x�U�����
	public void machineDataHour();
	
	// ������e�o�Ӥp�ɲֿn���
	//���o�̷s���@�Ӥp�ɤ������
	public EquipmentHourRes machineNewHourData();
	
	// �������e���X�Ӥp�ɲֿn�����
	//���o���餣�P�������x�U�����
	public EquipmentHoursDayRes machineHoursData();
	
	//�S�w���x�C�p�ɸ��
	public EquipmentHourRes machineDayData(String name);
	
	//�S�w���x�g�������
	public EquipmentWeekRes machineWeekAvg(String name);
	
	//�S�w���x���׸��
	public MaintenanceRes equipmentRecord(String name);
	
	//�S�w���x���׸�Ʋέp
	public RecordStatisticsRes recordStatistics(String name);
	
	// �s�W���׸��
	public BaseRes addRecord(RecordReq req);
	
	// ��s���׸��
	public BaseRes updateRecord(RecordReq req);
	
	// �R�����׸��
	public BaseRes deleteRecord(RecordDeleteReq req);
	
	// ���� �渹
	//���o���馳�Ͳ����渹�����q�P���x���U�����
	public OrderAndMachineRes orderDataDay();
}
