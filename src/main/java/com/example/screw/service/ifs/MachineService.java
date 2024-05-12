package com.example.screw.service.ifs;

import java.time.LocalDate;
import java.util.List;

import com.example.screw.entity.ReceiveData;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.StatusAndOrderRes;
import com.example.screw.vo.VoltageRes;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MachineService {
	
	// 儲存機台一天平均的資料
	public void machineDataDay ();
	
	// 取所有機台的最新資料
	public StatusAndOrderRes findmachineDataNow();
	
	// 取得機台一週的平均各項資料
	public EquipmentRes machineDataWeek(String machineName);
	
	// 取得機台一個月的平均各項資料
	public EquipmentRes machineDataMonth(String machineName);
	
	// 取得機台一年的平均各項資料
	public EquipmentRes machineDataYear(String machineName);
	
	//取得目前電壓
	public VoltageRes getVoltage();
	
	//更新電壓
	public BaseRes updateVoltage(double voltage);
	
	// 取得當期累積電度
	public ElectricityRes electricityPeriod(double voltage);
	
	//取得現有設備的名稱
	public MachineNameRes findMachineName();
	
	//刪除設備
	public BaseRes deleteMachine(MachineNameReq req);
	
	//新增設備
	public BaseRes addMachine(String machineName);
	
}
