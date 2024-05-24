package com.example.screw.vo;

import java.time.LocalDateTime;

public class OrderAndMachine {

	private String orderNumber;
	
	private int aim;
	
	private int pass;
	
	private double passAvg;
	
	private String name;
	
	private LocalDateTime finishTime;
	
	private LocalDateTime updateTime;

	public OrderAndMachine() {
		super();
		
	}

	public OrderAndMachine(String orderNumber, int aim, int pass, double passAvg, String name, LocalDateTime finishTime,
			LocalDateTime updateTime) {
		super();
		this.orderNumber = orderNumber;
		this.aim = aim;
		this.pass = pass;
		this.passAvg = passAvg;
		this.name = name;
		this.finishTime = finishTime;
		this.updateTime = updateTime;
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

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public double getPassAvg() {
		return passAvg;
	}

	public void setPassAvg(double passAvg) {
		this.passAvg = passAvg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	
}
