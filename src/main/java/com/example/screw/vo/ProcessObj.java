package com.example.screw.vo;

public class ProcessObj {   // 單筆製程使用物/排放物的 obj
	
	private String name;    // 使用物/排放物的名稱
	
	private int amount;     // 使用量/排放量

	public ProcessObj() {
		super();
	}

	public ProcessObj(String name, int amount) {
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
