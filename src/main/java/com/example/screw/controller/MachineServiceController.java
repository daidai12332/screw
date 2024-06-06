package com.example.screw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.screw.service.ifs.MachineService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.EquipmentHourRes;
import com.example.screw.vo.EquipmentHoursDayRes;
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

@CrossOrigin
@RestController
public class MachineServiceController {
	
	@Autowired
	private MachineService machineService;	
		
	// ����

	// ���o���馳�Ͳ����渹�����q�P���x���U�����
	@GetMapping(value = "screw/orderDataDay")
	public  OrderAndMachineRes orderDataDay() {
		return machineService.orderDataDay();
	}
	
	// ���Ҧ����x���̷s���
	@GetMapping(value = "screw/findmachineDataNow")
	public StatusAndOrderRes findmachineDataNow() {
		return machineService.findmachineDataNow();
	}

	// �������e���X�Ӥp�ɲֿn�����
	@GetMapping(value = "screw/machineHoursData")
	public  EquipmentHoursDayRes machineHoursData() {
		return machineService.machineHoursData();
	}

	// ������e�o�Ӥp�ɲֿn���
	@GetMapping(value = "screw/machineNewHourData")
	public EquipmentHourRes machineNewHourData() {
		return machineService.machineNewHourData();
	}
	
	// 

	// �Բӥ\��
	
	// ���o�Ҧ��]�ƪ��򥻸��
	@PostMapping(value = "screw/findMachines")
	public MachineNameRes findMachines() {
		return machineService.findMachines();
	}
	
	//�S�w���x���׸��
	@PostMapping(value = "screw/equipmentRecord")
	public MaintenanceRes equipmentRecord(@RequestParam(value = "name") String machineName) {
			return machineService.equipmentRecord(machineName);
	}

	///�S�w���x�C�p�ɸ��
	@PostMapping(value = "screw/machineDayData")
	public EquipmentHourRes machineDayData(@RequestParam(value = "name") String machineName) {
			return machineService.machineDayData(machineName);
	}
	
	//�S�w���x�g�������
	@PostMapping(value = "screw/machineWeekAvg")
	public EquipmentWeekRes machineWeekAvg(@RequestParam(value = "name") String machineName) {
			return machineService.machineWeekAvg(machineName);
	}
	
	// ���o�S�w�]�ƪ����ײέp���
	@PostMapping(value = "screw/recordStatistics")
	public RecordStatisticsRes recordStatistics(@RequestParam(value = "name") String machineName) {
			return machineService.recordStatistics(machineName);
	}

	//
	
	// ��x
	// �ާ@�\��
	
	//�s�W���x
	@PostMapping(value = "screw/addMachine")
	public BaseRes addMachine(@RequestBody UpdateEquipmentReq req) {
		return machineService.addMachine(req);
	}

	//�s����x
	@PostMapping(value = "screw/updateMachine")
	public BaseRes updateMachine(@RequestBody UpdateEquipmentReq req) {
		return machineService.updateMachine(req);
	}

	//�R�����x
	@PostMapping(value = "screw/deleteMachine")
	public BaseRes deleteMachine(@RequestBody MachineNameReq req) {
		return machineService.deleteMachine(req);
	}
		
	//�s�W���׸��
	@PostMapping(value = "screw/addRecord")
	public BaseRes addRecord(@RequestBody RecordReq req) {
			return machineService.addRecord(req);
	}
	
	//��s���׸��
	@PostMapping(value = "screw/updateRecord")
	public BaseRes updateRecord(@RequestBody RecordReq req) {
			return machineService.updateRecord(req);
	}
	
	//�R�����׸��
	@PostMapping(value = "screw/deleteRecord")
	public BaseRes deleteRecord(@RequestBody RecordDeleteReq req) {
			return machineService.deleteRecord(req);
	}
	
	// ��ΰ�
	
	// �e�x�S�w���x���
	// ���o�S�w���x�@�g�������U�����
//	@PostMapping(value = "screw/machineDataWeek")
//	public EquipmentRes machineDataWeek(@RequestParam(value = "name") String machineName) {
//		return machineService.machineDataWeek(machineName);
//	}
	
	// �e�x�S�w���x���
	// ���o���x�@�Ӥ몺�����U�����
//	@PostMapping(value = "screw/machineDataMonth")
//	public EquipmentRes machineDataMonth(@RequestParam(value = "name") String machineName) {
//		return machineService.machineDataMonth(machineName);
//	}
	
	// �e�x�S�w���x���
	// ���o���x�@�~�������U�����
//	@PostMapping(value = "screw/machineDataYear")
//	public EquipmentRes machineDataYear(@RequestParam(value = "name") String machineName) {
//		return machineService.machineDataYear(machineName);
//	}
	
//	@PostMapping(value = "screw/getVoltage")
//	public VoltageRes getVoltage() {
//		return machineService.getVoltage();
//	}
		
//	@PostMapping(value = "screw/electricityPeriod")
//	public ElectricityRes electricityPeriod() {
//		return machineService.electricityPeriod();
//	}
	
}
