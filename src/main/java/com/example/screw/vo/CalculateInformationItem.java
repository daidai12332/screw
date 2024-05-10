package com.example.screw.vo;

import java.util.List;

public class CalculateInformationItem {
		
	private String orderNo;   // �渹
	
	private String name;   // �W��

	private double carbonEmission;  // �p��X���ұƵ��G
	
	private int aim;       // �ؼжq
	
	private int produce;   // ���q
	
	private int weight;    // ���q 
	
	private List<ProduceObj> raw;    // ���
	
	private List<ProduceObj> process;  // �s�{

	public CalculateInformationItem() {
		super();
	}

	public CalculateInformationItem(String orderNo, String name, double carbonEmission, int aim, int produce, int weight,
			List<ProduceObj> raw, List<ProduceObj> process) {
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
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

	public List<ProduceObj> getRaw() {
		return raw;
	}

	public void setRaw(List<ProduceObj> raw) {
		this.raw = raw;
	}

	public List<ProduceObj> getProcess() {
		return process;
	}

	public void setProcess(List<ProduceObj> process) {
		this.process = process;
	}
	
}
