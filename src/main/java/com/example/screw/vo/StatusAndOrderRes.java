package com.example.screw.vo;

import java.util.List;

import com.example.screw.entity.ReceiveData;

public class StatusAndOrderRes extends BaseRes{

	private List<ReceiveData> machineDataList;

	public StatusAndOrderRes() {
		super();
	}

	public StatusAndOrderRes(int code, String message) {
		super(code, message);		
	}

	public StatusAndOrderRes(int code, String message,List<ReceiveData> machineDataList) {
		super(code, message);
		this.machineDataList = machineDataList;
	}



	public List<ReceiveData> getMachineData() {
		return machineDataList;
	}

	public void setMachineData(List<ReceiveData> machineData) {
		this.machineDataList = machineData;
	}
	
	
}
