package com.example.screw.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.screw.constants.RtnCode;
import com.example.screw.entity.Equipment;
import com.example.screw.entity.ReceiveData;
import com.example.screw.repository.MachineDataDao;
import com.example.screw.repository.ReceiveDataDao;
import com.example.screw.service.ifs.MachineService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.ElectricityPeriod;
import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.ReceiveDataCurrent;
import com.example.screw.vo.ReceiveDataLong;
import com.example.screw.vo.ReceiveDataStatus;
import com.example.screw.vo.StatusAndOrderRes;
import com.example.screw.vo.VoltageRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableScheduling
@Transactional
@Service
public class MachineServiceImpl implements MachineService{
	
	@Autowired
	private MachineDataDao machineDataDao;
	
	@Autowired
	private ReceiveDataDao receiveDataDao;

	@Override  
	@Scheduled(cron = "0 55 23 * * ?")
	public void machineDataDay(){
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime tomorrow = currentDate.plusDays(1);
		// 
		List<ReceiveDataLong> machineDay = machineDataDao.machineDataDay(tomorrow);
		List<ReceiveDataStatus> machineStatusRun = machineDataDao.machineDataStatusRun(tomorrow);
		List<ReceiveDataStatus> machineStatusIdle = machineDataDao.machineDataStatusIdle(tomorrow);
		List<ReceiveDataStatus> machineStatusError = machineDataDao.machineDataStatusError(tomorrow);
		List<ReceiveDataCurrent> runCurrentAvg = machineDataDao.machineRunCurrentAvg();
		List<ReceiveDataCurrent> idleCurrentAvg = machineDataDao.machineIdleCurrentAvg();
		List<ReceiveDataCurrent> errorCurrentAvg = machineDataDao.machineErrorCurrentAvg();
		
		
		List<Equipment> equList = new ArrayList<>();
		LocalDate today = LocalDate.now();
		Equipment equ = new Equipment();
		// 各狀態集比例
		double passAvg = 0;
		double currentAvg = 0;
		double runAvg = 0;
		double idleAvg = 0;
		double errorAvg = 0;
		double runIT = 0;
		double idleIT = 0;
		double errorIT = 0;
		int index = 0;
		double statusSum = 0;
		
		for(ReceiveDataLong item : machineDay) {
			passAvg = (double)item.getPass()/(double)(item.getPass()+item.getNg());
			equ.setDataPassAvg(passAvg);
			currentAvg = item.getCurrent();
			equ.setDataCurrentAvg(currentAvg);
			equ.setName(item.getName());

			equ.setDataDate(today);

			

			equ.setDel(false);
			runIT = runCurrentAvg.get(index).getCurrent()*24/1000;
			equ.setRunIT(runIT);
			idleIT = idleCurrentAvg.get(index).getCurrent()*24/1000;
			equ.setIdleIT(idleIT);
			errorIT = errorCurrentAvg.get(index).getCurrent()*24/1000;
			equ.setErrorIT(errorIT);
			statusSum = (double)machineStatusIdle.get(index).getStatus()+(double)machineStatusRun.get(index).getStatus()+(double)machineStatusError.get(index).getStatus(); 
			runAvg = (double)machineStatusRun.get(index).getStatus()/statusSum;
			equ.setDataRunAvg(runAvg);
			idleAvg = (double)machineStatusIdle.get(index).getStatus()/statusSum;
			equ.setDataIdleAvg(idleAvg);
			errorAvg = (double)machineStatusError.get(index).getStatus()/statusSum;
			equ.setDataErrorAvg(errorAvg);
			index++;
			machineDataDao.save(equ);
//			equList.add(equ);
			
				
		}
//		System.out.println(equList.get(index).getName());
//		System.out.println(equList.size());
//		machineDataDao.saveAll(equList);
		return;
	}

	@Override
	public StatusAndOrderRes findmachineDataNow() {
		// 取所有機台最新的資料
		return new StatusAndOrderRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), receiveDataDao.machineDataNow());
	}
	
	@Override
	public EquipmentRes machineDataWeek(String machineName) {
		
		if(!StringUtils.hasText(machineName)) {
			return new EquipmentRes(RtnCode.MACHINE_NAME_NOT_FOUND.getCode(), RtnCode.MACHINE_NAME_NOT_FOUND.getMessage());
		}
		
		return new EquipmentRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineDataStatusWeek(machineName));	
	}

	@Override
	public EquipmentRes machineDataMonth(String machineName) {
		
		if(!StringUtils.hasText(machineName)) {
			return new EquipmentRes(RtnCode.MACHINE_NAME_NOT_FOUND.getCode(), RtnCode.MACHINE_NAME_NOT_FOUND.getMessage());
		}
		
		return new EquipmentRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineDataStatusMonth(machineName));
	}

	@Override
	public EquipmentRes machineDataYear(String machineName) {
		
		if(!StringUtils.hasText(machineName)) {
			return new EquipmentRes(RtnCode.MACHINE_NAME_NOT_FOUND.getCode(), RtnCode.MACHINE_NAME_NOT_FOUND.getMessage());
		}
		
		return new EquipmentRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineDataStatusYear(machineName));
	}
	
	@Override
	public VoltageRes getVoltage() {
		
		return new VoltageRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineVoltage());
	}
	
	@Override
	public BaseRes updateVoltage(double voltage) {
		
		machineDataDao.updateVoltage(voltage);
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}
	
	@Override
	public ElectricityRes electricityPeriod(double voltage) {
		
		LocalDate today = LocalDate.now();
		
		if(voltage == 0 ) {
			return new ElectricityRes (RtnCode.VOLTAGE_CANNOT_BE_ZERO.getCode(), RtnCode.VOLTAGE_CANNOT_BE_ZERO.getMessage());
		}
		
		if(today.getMonthValue() % 2 == 0) {
			LocalDate period = LocalDate.of(today.getYear(), today.getMonthValue()-1, 1);
			ElectricityRes res = caculateElectricity(period, voltage);
			return res;
			
		}else {
			LocalDate period = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
			ElectricityRes res = caculateElectricity(period, voltage);
			return res;
		}
		

	}

	private ElectricityRes caculateElectricity(LocalDate period, double voltage) {
		ElectricityRes electricity = new ElectricityRes();
		 ElectricityPeriod machineIT = machineDataDao.machineITAll(period);
		double runElectricity = 0;
		double idleElectricity = 0;
		double errorElectricity = 0;
		runElectricity = voltage*machineIT.getRunIT();
		idleElectricity = voltage*machineIT.getIdleIT();
		errorElectricity = voltage*machineIT.getErrorIT();
		electricity.setRunElectricity(runElectricity);
		electricity.setIdleElectricity(idleElectricity);
		electricity.setErrorElectricity(errorElectricity);
		return new ElectricityRes (RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), electricity.getRunElectricity(), electricity.getIdleElectricity(), electricity.getErrorElectricity());
	}

	@Override
	public MachineNameRes findMachineName() {
		
		return new MachineNameRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineName());
	}

	@Override
	public BaseRes deleteMachine(MachineNameReq req) {
		
		if(CollectionUtils.isEmpty(req.getMachineNameList())) {
			return new BaseRes(RtnCode.MACHINE_NAME_CANNOT_BE_NULL.getCode(), RtnCode.MACHINE_NAME_CANNOT_BE_NULL.getMessage());
		}
		
		for(String item: req.getMachineNameList()) {
			machineDataDao.machineDelete(item);
		}
		
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes addMachine(String machineName) {
		
		if(!StringUtils.hasText(machineName)) {
			return new BaseRes(RtnCode.MACHINE_NAME_CANNOT_BE_NULL.getCode(), RtnCode.MACHINE_NAME_CANNOT_BE_NULL.getMessage());
		}

		Equipment equ = new Equipment();
		LocalDate today = LocalDate.of(2020, 1, 1);
		equ.setName(machineName);
		equ.setDataDate(today);
		machineDataDao.save(equ);
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	

	
}
