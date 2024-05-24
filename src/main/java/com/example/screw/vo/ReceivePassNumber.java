package com.example.screw.vo;

public class ReceivePassNumber {
	
	private String name;
	
	private String orderNumber;
	
	private double pass;

	public ReceivePassNumber() {
		super();
	}

	public ReceivePassNumber(String name, String orderNumber, double pass) {
		super();
		this.name = name;
		this.orderNumber = orderNumber;
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getPass() {
		return pass;
	}

	public void setPass(double pass) {
		this.pass = pass;
	}

}
