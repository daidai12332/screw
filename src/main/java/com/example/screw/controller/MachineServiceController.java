package com.example.screw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.screw.service.ifs.MachineService;
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

@CrossOrigin
@RestController
public class MachineServiceController {
	
	@Autowired
	private MachineService machineService;
	
	// ����
	// ���Ҧ����x���̷s���
	@PostMapping(value = "screw/findmachineDataNow")
	public StatusAndOrderRes findmachineDataNow() {
		return machineService.findmachineDataNow();
	}
	
	// �e�x�S�w���x���
	// ���o�S�w���x�@�g�������U�����
	@PostMapping(value = "screw/machineDataWeek")
	public EquipmentRes machineDataWeek(@RequestParam(value = "name") String machineName) {
		return machineService.machineDataWeek(machineName);
	}
	
	// �e�x�S�w���x���
	// ���o���x�@�Ӥ몺�����U�����
	@PostMapping(value = "screw/machineDataMonth")
	public EquipmentRes machineDataMonth(@RequestParam(value = "name") String machineName) {
		return machineService.machineDataMonth(machineName);
	}
	
	// �e�x�S�w���x���
	// ���o���x�@�~�������U�����
	@PostMapping(value = "screw/machineDataYear")
	public EquipmentRes machineDataYear(@RequestParam(value = "name") String machineName) {
		return machineService.machineDataYear(machineName);
	}
	
//	@PostMapping(value = "screw/getVoltage")
//	public VoltageRes getVoltage() {
//		return machineService.getVoltage();
//	}
	
	// ��x
	//��s���x��T
	@PostMapping(value = "screw/updateMachine")
	public BaseRes updateMachine(@RequestBody UpdateEquipmentReq req) {
		return machineService.updateMachine(req);
	}
	
//	@PostMapping(value = "screw/electricityPeriod")
//	public ElectricityRes electricityPeriod() {
//		return machineService.electricityPeriod();
//	}
	
	// ��x
	//���o�{���]�ƪ��U�����
	@PostMapping(value = "screw/findMachines")
	public MachineNameRes findMachines() {
		return machineService.findMachines();
	}
	
	// ��x
	//�R���]��
	@PostMapping(value = "screw/deleteMachine")
	public BaseRes deleteMachine(@RequestBody MachineNameReq req) {
		return machineService.deleteMachine(req);
	}
	
	// ��x
	//�s�W�]��
	@PostMapping(value = "screw/addMachine")
	public BaseRes addMachine(@RequestBody UpdateEquipmentReq req) {
		return machineService.addMachine(req);
	}

	// ������e�o�Ӥp�ɲֿn���
	//���o�̷s���@�Ӥp�ɤ������
	@PostMapping(value = "screw/machineNewHourData")
	public EquipmentHourRes machineNewHourData() {
		return machineService.machineNewHourData();
	}
	
	// �������e���X�Ӥp�ɲֿn�����
	//���o���餣�P�������x�U�����
	@PostMapping(value = "screw/machineHoursData")
	public  EquipmentHoursDayRes machineHoursData() {
		return machineService.machineHoursData();
	}
	
	// ���� �渹
	//���o���馳�Ͳ����渹�����q�P���x���U�����
	@PostMapping(value = "screw/orderDataDay")
	public  OrderAndMachineRes orderDataDay() {
		return machineService.orderDataDay();
	}
	
	// ��x
	///�S�w���x�C�p�ɸ��
	@PostMapping(value = "screw/machineDayData")
	public EquipmentHourRes machineDayData(@RequestParam(value = "name") String machineName) {
			return machineService.machineDayData(machineName);
	}
	
	// ��x
	//�S�w���x�g�������
	@PostMapping(value = "screw/machineWeekAvg")
	public EquipmentWeekRes machineWeekAvg(@RequestParam(value = "name") String machineName) {
			return machineService.machineWeekAvg(machineName);
	}
	
	// ��x
	//�S�w���x���׸��
	@PostMapping(value = "screw/equipmentRecord")
	public MaintenanceRes equipmentRecord(@RequestParam(value = "name") String machineName) {
			return machineService.equipmentRecord(machineName);
	}
	
	// ��x
	//�S�w���x���׸�Ʋέp
	@PostMapping(value = "screw/recordStatistics")
	public RecordStatisticsRes recordStatistics(@RequestParam(value = "name") String machineName) {
			return machineService.recordStatistics(machineName);
	}
	
	// ��x
	//�s�W���׸��
	@PostMapping(value = "screw/addRecord")
	public BaseRes addRecord(@RequestBody RecordReq req) {
			return machineService.addRecord(req);
	}
	
	// ��x
	//��s���׸��
	@PostMapping(value = "screw/updateRecord")
	public BaseRes updateRecord(@RequestBody RecordReq req) {
			return machineService.updateRecord(req);
	}
	
	// ��x
	//�R�����׸��
	@PostMapping(value = "screw/deleteRecord")
	public BaseRes deleteRecord(@RequestBody RecordDeleteReq req) {
			return machineService.deleteRecord(req);
	}
}
