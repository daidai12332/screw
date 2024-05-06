package com.example.screw.vo;

public class ProcessObj {   // 單筆製程使用物or排放物的 obj
	
	private String name;    // 使用物or排放物的名稱
	
	private double amount;     // 使用量or排放量

	public ProcessObj() {
		super();
	}

	public ProcessObj(String name, double amount) {
		super();
		this.name = name;
		this.amount = amount;
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
	
	
}
