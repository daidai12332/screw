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
	
	// 儲存機台一天平均的資料
	public void machineDataDay ();
	
	// 首頁
	// 取所有機台的最新資料
	public StatusAndOrderRes findmachineDataNow();
	
	// 前台特定機台資料
	// 取得特定機台一週的平均各項資料
	public EquipmentRes machineDataWeek(String machineName);
	
	// 前台特定機台資料
	// 取得機台一個月的平均各項資料
	public EquipmentRes machineDataMonth(String machineName);
	
	// 前台特定機台資料
	// 取得機台一年的平均各項資料
	public EquipmentRes machineDataYear(String machineName);
	
	
//	//取得目前電壓
//	public VoltageRes getVoltage();
	
	// 後台
	//更新電壓
	public BaseRes updateVoltage(double voltage, String machineName);
	
//	// 取得當期累積電度
//	public ElectricityRes electricityPeriod();
	
	// 後台
	//取得現有設備的名稱
	public MachineNameRes findMachineName();
	
	// 後台
	//刪除設備
	public BaseRes deleteMachine(MachineNameReq req);
	
	// 後台
	//新增設備
	public BaseRes addMachine(String machineName);
	
	//每小時儲存機台各項資料
	public void machineDataHour();
	
	// 首頁當前這個小時累積資料
	//取得最新的一個小時內的資料
	public EquipmentHourRes machineNewHourData();
	
	// 首頁之前的幾個小時累積的資料
	//取得今日不同類型機台各項資料
	public EquipmentHoursDayRes machineHoursData();
	
	// 首頁 單號
	//取得今日有生產之單號的產量與機台等各項資料
	public OrderAndMachineRes orderDataDay();
}
