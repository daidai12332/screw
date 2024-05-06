package com.example.screw.vo;

public class CalculateItem {
	
	private int orderNo;            // �q��s��

	private String name;            // �q��W��
	
	private double carbonEmission;  // �p��X���ұƵ��G

	public CalculateItem() {
		super();
	}
	
	public CalculateItem(int orderNo, String name, double carbonEmission) {
		super();
		this.orderNo = orderNo;
		this.name = name;
		this.carbonEmission = carbonEmission;
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
	
	
}
