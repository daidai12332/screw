package com.example.screw.vo;

import java.util.List;

public class CalculateInformationItem {
		
	private int orderNo;   // 單號
	
	private String name;   // 名稱

	private double carbonEmission;  // 計算出的碳排結果
	
	private int aim;       // 目標量
	
	private int produce;   // 產量
	
	private int weight;    // 重量 
	
	private List<RawObj> raw;    // 原料
	
	private List<ProcessObj> process;  // 製程

	public CalculateInformationItem() {
		super();
	}

	public CalculateInformationItem(int orderNo, String name, double carbonEmission, int aim, int produce, int weight,
			List<RawObj> raw, List<ProcessObj> process) {
		super();
		this.orderNo = orderNo;
		this.name = name;
		this.carbonEmission = carbonEmission;
		this.aim = aim;
		this.produce = produce;
		this.weight = weight;
		this.raw = raw;
		this.process = process;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCarbonEmission() {
		return carbonEmission;
	}

	public void setCarbonEmission(double carbonEmission) {
		this.carbonEmission = carbonEmission;
	}

	public int getAim() {
		return aim;
	}

	public void setAim(int aim) {
		this.aim = aim;
	}

	public int getProduce() {
		return produce;
	}

	public void setProduce(int produce) {
		this.produce = produce;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<RawObj> getRaw() {
		return raw;
	}

	public void setRaw(List<RawObj> raw) {
		this.raw = raw;
	}

	public List<ProcessObj> getProcess() {
		return process;
	}

	public void setProcess(List<ProcessObj> process) {
		this.process = process;
	}
	
}
