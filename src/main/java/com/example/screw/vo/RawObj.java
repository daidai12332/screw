package com.example.screw.vo;


public class RawObj {   // �浧��ƪ� obj
	
	private String name;    // ��ƦW��
	
	private int amount;     // �ζq
	
	private int scopeOne;   // scope1 �ұƩ�Y��
	
	private int scopeTwo;   // scope 2 �ұƩ�Y��

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
