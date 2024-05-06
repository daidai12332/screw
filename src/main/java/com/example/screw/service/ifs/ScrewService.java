package com.example.screw.service.ifs;

import java.util.List;

import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProcessObj;
import com.example.screw.vo.RawObj;

public interface ScrewService {

	// 新增單號
	public BaseRes addOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw, List<ProcessObj> process);

	// 編輯單號
	public BaseRes editOrder(int orderNo, String name, int aim, int weight, List<RawObj> raw, List<ProcessObj> process);

	// 每日結算各單號累積耗電量和生產量(Schedule)
	public void settleOrderPower();

	// 計算碳排(用於碳排呈現): 參數(null) 傳回(每筆單號的資料庫資料、碳排值)
	public CalculateInformationRes calculateCarbonEmission();
	
	// 更新電力使用量(用於碳排細節呈現): 參數(正在查看的單號) 傳回(最新每顆螺絲的使用電度)
	public PowerUpdateRes powerUpdate(int orderNo, int aim, int produce,int weight, double powerUsage);
}
