package com.example.screw.vo;

public class ReceiveDataOrder {
	
	private String name;
	
	private String orderNumber;
	
	private long pass;
	
	private long ng;

	public ReceiveDataOrder() {
		super();
	}

	public ReceiveDataOrder(String name, String orderNumber, long pass, long ng) {
		super();
		this.name = name;
		this.orderNumber = orderNumber;
		this.pass = pass;
		this.ng = ng;
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

	public long getPass() {
		return pass;
	}

	public void setPass(long pass) {
		this.pass = pass;
	}

	public long getNg() {
		return ng;
	}

	public void setNg(long ng) {
		this.ng = ng;
	}

}
