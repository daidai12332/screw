package com.example.screw.vo;

public class ProcessObj {   // �浧�s�{�ϥΪ�or�Ʃ񪫪� obj
	
	private String name;    // �ϥΪ�or�Ʃ񪫪��W��
	
	private double amount;     // �ϥζqor�Ʃ�q

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
