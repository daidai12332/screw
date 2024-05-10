package com.example.screw.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProduceObj {   // �浧��ƪ� obj
	
	@NotBlank(message = "�W�٤��ର��")
	private String name;    // ��ƦW��
	
	@Min(value = 0, message = "�ζq���o���t")
	private double amount;     // �ζq
	
	@Min(value = 0, message = "�ұƩ�Y�Ƥ��o���t")
	private double carbonCoefficient;   // �ұƩ�Y��

	public ProduceObj() {
		super();
	}

	public ProduceObj(String name, double amount, double carbonCoefficient) {
		super();
		this.name = name;
		this.amount = amount;
		this.carbonCoefficient = carbonCoefficient;
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

	public double getCarbonCoefficient() {
		return carbonCoefficient;
	}

	public void setCarbonCoefficient(double carbonCoefficient) {
		this.carbonCoefficient = carbonCoefficient;
	}
	
}
