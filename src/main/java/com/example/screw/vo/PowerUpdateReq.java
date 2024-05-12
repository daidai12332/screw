package com.example.screw.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class PowerUpdateReq {

	@NotEmpty(message = "�渹���i����")
	private String orderNumber;   // �渹
	
	@Min(value = 1, message = "�ؼжq�ݦܤ֬� {value}")
	private int aim;              // �ؼжq
	
	@Min(value = 0, message = "�ֿn���q���o���t")
	private int produce;          // �ֿn���q(���]�t����)
	
	@Min(value = 0, message = "�����������q���o���t")
	private int weight;           // �C�����������q(g)
	
	@Min(value = 0, message = "�ֿn�q�פ��o���t")
	private double powerUsage;    // �ֿn�q��(���]�t����)

	public PowerUpdateReq() {
		super();
	}

	public PowerUpdateReq(String orderNumber, int aim, int produce, int weight, double powerUsage) {
		super();
		this.orderNumber = orderNumber;
		this.aim = aim;
		this.produce = produce;
		this.weight = weight;
		this.powerUsage = powerUsage;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getAim() {
		return aim;
	}

	public void setAim(int aim) {
		this.aim = aim;
	}

	public int getProduce() {
		return produce;
	}

	public void setProduce(int produce) {
		this.produce = produce;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public double getPowerUsage() {
		return powerUsage;
	}

	public void setPowerUsage(double powerUsage) {
		this.powerUsage = powerUsage;
	}
	
}
