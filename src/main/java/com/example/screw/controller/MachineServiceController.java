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
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.StatusAndOrderRes;
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
	
	@PostMapping(value = "screw/getVoltage")
	public VoltageRes getVoltage() {
		return machineService.getVoltage();
	}
	
	@PostMapping(value = "screw/updateVoltage")
	public BaseRes updateVoltage(@RequestParam(value = "data_run_avg") double voltage) {
		return machineService.updateVoltage(voltage);
	}
	
	@PostMapping(value = "screw/electricityPeriod")
	public ElectricityRes electricityPeriod(@RequestParam(value = "data_run_avg") double voltage) {
		return machineService.electricityPeriod(voltage);
	}
	
	@PostMapping(value = "screw/findMachineName")
	public MachineNameRes findMachineName() {
		return machineService.findMachineName();
	}
	
	@PostMapping(value = "screw/deleteMachine")
	public BaseRes deleteMachine(@RequestBody MachineNameReq req) {
		return machineService.deleteMachine(req);
	}
	
	@PostMapping(value = "screw/addMachine")
	public BaseRes addMachine(@RequestParam(value = "name") String machineName) {
		return machineService.addMachine(machineName);
	}

	
}
