package com.example.screw.vo;

public class ProcessObj {   // �浧�s�{�ϥΪ�/�Ʃ񪫪� obj
	
	private String name;    // �ϥΪ�/�Ʃ񪫪��W��
	
	private int amount;     // �ϥζq/�Ʃ�q

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
