package com.example.screw.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.screw.constants.RtnCode;
import com.example.screw.entity.Equipment;
import com.example.screw.entity.ReceiveData;
import com.example.screw.repository.MachineDataDao;
import com.example.screw.service.ifs.MachineService;
import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.StatusAndOrderRes;

@EnableScheduling
@Transactional
@Service
public class MachineServiceImpl implements MachineService{
	
	@Autowired
	private MachineDataDao machineDataDao;

	@Override  
	@Scheduled(cron = "0 55 23 * * ?")
	public void machineDataDay() {
		// 
		List<ReceiveData> machineDay = machineDataDao.machineDataDay();
		List<ReceiveData> machineStatusRun = machineDataDao.machineDataStatusRun();
		List<ReceiveData> machineStatusIdle = machineDataDao.machineDataStatusIdle();
		List<ReceiveData> machineStatusError = machineDataDao.machineDataStatusError();
		List<ReceiveData> runCurrentAvg = machineDataDao.machineRunCurrentAvg();
		List<ReceiveData> idleCurrentAvg = machineDataDao.machineIdleCurrentAvg();
		List<ReceiveData> errorCurrentAvg = machineDataDao.machineErrorCurrentAvg();
		
		Equipment equ = null;
		List<Equipment> equList = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();
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
		
		for(ReceiveData item : machineDay) {
			passAvg = (double)item.getPass()/(double)(item.getPass()+item.getNg());
			equ.setDataPassAvg(passAvg);
			currentAvg = item.getCurrent();
			equ.setDataCurrentAvg(currentAvg);
			equ.setName(item.getName());
			equ.setDataDate(currentDate);
			equ.setDel(false);
			runIT = runCurrentAvg.get(index).getCurrent()*24/1000;
			equ.setRunIT(runIT);
			idleIT = idleCurrentAvg.get(index).getCurrent()*24/1000;
			equ.setIdleIT(idleIT);
			errorIT = errorCurrentAvg.get(index).getCurrent()*24/1000;
			equ.setErrorIT(errorIT);
			statusSum = Double.parseDouble(machineStatusIdle.get(index).getStatus())+Double.parseDouble(machineStatusRun.get(index).getStatus())+Double.parseDouble(machineStatusError.get(index).getStatus()); 
			runAvg = Double.parseDouble(machineStatusRun.get(index).getStatus())/statusSum;
			equ.setDataRunAvg(runAvg);
			idleAvg = Double.parseDouble(machineStatusIdle.get(index).getStatus())/statusSum;
			equ.setDataIdleAvg(idleAvg);
			errorAvg = Double.parseDouble(machineStatusError.get(index).getStatus())/statusSum;
			equ.setDataErrorAvg(errorAvg);
			index++;
			equList.add(equ);
		}
		
		machineDataDao.saveAll(equList);
		
		return;
	}

	@Override
	public StatusAndOrderRes findmachineDataNow() {
		// 取所有機台最新的資料
		return new StatusAndOrderRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineDataNow());
	}
	
	@Override
	public EquipmentRes machineDataWeek(String machineName) {
		
		
		return new EquipmentRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineDataStatusWeek(machineName));	
	}

	@Override
	public EquipmentRes machineDataMonth(String machineName) {
		
		
		return new EquipmentRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineDataStatusMonth(machineName));
	}

	@Override
	public EquipmentRes machineDataYear(String machineName) {
		
		return new EquipmentRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), machineDataDao.machineDataStatusYear(machineName));
	}
	
	@Override
	public ElectricityRes electricityPeriod(double voltage) {
		
		LocalDate today = LocalDate.now();
		
		if(voltage == 0 ) {
			return new ElectricityRes (RtnCode.VOLTAGE_CANNOT_ZERO.getCode(), RtnCode.VOLTAGE_CANNOT_ZERO.getMessage());
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
		ElectricityRes electricity = null;
		Equipment machineIT = machineDataDao.machineITAll(period);
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
	
}
