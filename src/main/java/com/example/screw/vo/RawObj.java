package com.example.screw.vo;


public class RawObj {   // 單筆原料的 obj
	
	private String name;    // 原料名稱
	
	private int amount;     // 用量
	
	private int scopeOne;   // scope1 碳排放係數
	
	private int scopeTwo;   // scope 2 碳排放係數

	public RawObj() {
		super();
	}

	public RawObj(String name, int amount, int scopeOne, int scopeTwo) {
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getScopeOne() {
		return scopeOne;
	}

	public void setScopeOne(int scopeOne) {
		this.scopeOne = scopeOne;
	}

	public int getScopeTwo() {
		return scopeTwo;
	}

	public void setScopeTwo(int scopeTwo) {
		this.scopeTwo = scopeTwo;
	}
	
}
