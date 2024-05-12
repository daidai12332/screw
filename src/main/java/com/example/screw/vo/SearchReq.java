package com.example.screw.vo;

public class SearchReq {
	
	private String orderNumber;
	
	private String name;

	public SearchReq() {
		super();
	}

	public SearchReq(String orderNumber, String name) {
		super();
		this.orderNumber = orderNumber;
		this.name = name;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
