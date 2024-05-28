package com.example.screw.vo;

import java.util.List;

import com.example.screw.entity.ReceiveData;

public class StatusAndOrderRes extends BaseRes{

	private List<ReceiveDataNew> machineDataList;

	public StatusAndOrderRes() {
		super();
	}

	public StatusAndOrderRes(int code, String message) {
		super(code, message);		
	}

	public StatusAndOrderRes(int code, String message,List<ReceiveDataNew> machineDataList) {
		super(code, message);
		this.machineDataList = machineDataList;
	}



	public List<ReceiveDataNew> getMachineData() {
		return machineDataList;
	}

	public void setMachineData(List<ReceiveDataNew> machineData) {
		this.machineDataList = machineData;
	}
	
	
}
