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
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.OrderAndMachineRes;
import com.example.screw.vo.StatusAndOrderRes;
import com.example.screw.vo.UpdateEquipmentReq;
import com.example.screw.vo.VoltageRes;

@CrossOrigin
@RestController
public class MachineServiceController {
	
	@Autowired
	private MachineService machineService;
	
	@PostMapping(value = "screw/findmachineDataNow")
	public StatusAndOrderRes findmachineDataNow() {
		return machineService.findmachineDataNow();
	}
	
	@PostMapping(value = "screw/machineDataWeek")
	public EquipmentRes machineDataWeek(@RequestParam(value = "name") String machineName) {
		return machineService.machineDataWeek(machineName);
	}
	
	@PostMapping(value = "screw/machineDataMonth")
	public EquipmentRes machineDataMonth(@RequestParam(value = "name") String machineName) {
		return machineService.machineDataMonth(machineName);
	}
	
	@PostMapping(value = "screw/machineDataYear")
	public EquipmentRes machineDataYear(@RequestParam(value = "name") String machineName) {
		return machineService.machineDataYear(machineName);
	}
	
//	@PostMapping(value = "screw/getVoltage")
//	public VoltageRes getVoltage() {
//		return machineService.getVoltage();
//	}
	
	@PostMapping(value = "screw/updateMachine")
	public BaseRes updateMachine(@RequestBody UpdateEquipmentReq req) {
		return machineService.updateMachine(req);
	}
	
//	@PostMapping(value = "screw/electricityPeriod")
//	public ElectricityRes electricityPeriod() {
//		return machineService.electricityPeriod();
//	}
	
	@PostMapping(value = "screw/findMachines")
	public MachineNameRes findMachines() {
		return machineService.findMachines();
	}
	
	@PostMapping(value = "screw/deleteMachine")
	public BaseRes deleteMachine(@RequestBody MachineNameReq req) {
		return machineService.deleteMachine(req);
	}
	
	@PostMapping(value = "screw/addMachine")
	public BaseRes addMachine(@RequestBody UpdateEquipmentReq req) {
		return machineService.addMachine(req);
	}

	@PostMapping(value = "screw/machineNewHourData")
	public EquipmentHourRes machineNewHourData() {
		return machineService.machineNewHourData();
	}
	
	@PostMapping(value = "screw/machineHoursData")
	public  EquipmentHoursDayRes machineHoursData() {
		return machineService.machineHoursData();
	}
	
	@PostMapping(value = "screw/orderDataDay")
	public  OrderAndMachineRes orderDataDay() {
		return machineService.orderDataDay();
	}
}
