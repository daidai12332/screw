package com.example.screw.service.ifs;

import java.time.LocalDate;

import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.StatusAndOrderRes;

public interface MachineService {
	
	// �x�s���x�@�ѥ��������
	public void machineDataDay();
	
	// ���Ҧ����x���̷s���
	public StatusAndOrderRes findmachineDataNow();
	
	// ���o���x�@�g�������U�����
	public EquipmentRes machineDataWeek(String machineName);
	
	// ���o���x�@�Ӥ몺�����U�����
	public EquipmentRes machineDataMonth(String machineName);
	
	// ���o���x�@�~�������U�����
	public EquipmentRes machineDataYear(String machineName);
	
	// ���o����ֿn�q��
	public ElectricityRes electricityPeriod(double voltage);
	
	
}
