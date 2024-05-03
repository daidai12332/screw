package com.example.screw.vo;


public class RawObj {   // 單筆原料的 obj
	
	private String name;    // 原料名稱
	
	private double amount;     // 用量
	
	private double scopeOne;   // scope1 碳排放係數
	
	private double scopeTwo;   // scope 2 碳排放係數

	public RawObj() {
		super();
	}

	public RawObj(String name, double amount, double scopeOne, double scopeTwo) {
		super();
		this.name = name;
		this.amount = amount;
		this.scopeOne = scopeOne;
		this.scopeTwo = scopeTwo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getScopeOne() {
		return scopeOne;
	}

	public void setScopeOne(double scopeOne) {
		this.scopeOne = scopeOne;
	}

	public double getScopeTwo() {
		return scopeTwo;
	}

	public void setScopeTwo(double scopeTwo) {
		this.scopeTwo = scopeTwo;
	}
	
}
