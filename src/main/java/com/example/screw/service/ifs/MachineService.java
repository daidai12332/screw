package com.example.screw.service.ifs;

import java.time.LocalDate;

import com.example.screw.vo.ElectricityRes;
import com.example.screw.vo.EquipmentRes;
import com.example.screw.vo.StatusAndOrderRes;

public interface MachineService {
	
	// 儲存機台一天平均的資料
	public void machineDataDay();
	
	// 取所有機台的最新資料
	public StatusAndOrderRes findmachineDataNow();
	
	// 取得機台一週的平均各項資料
	public EquipmentRes machineDataWeek(String machineName);
	
	// 取得機台一個月的平均各項資料
	public EquipmentRes machineDataMonth(String machineName);
	
	// 取得機台一年的平均各項資料
	public EquipmentRes machineDataYear(String machineName);
	
	// 取得當期累積電度
	public ElectricityRes electricityPeriod(double voltage);
	
	
}
