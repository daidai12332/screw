package com.example.screw.vo;

public class ElectricityPeriod {

	private double runIT;  
	
	private double idleIT;  
	
	private double errorIT;

	public ElectricityPeriod() {
		super();
	}

	public ElectricityPeriod(double runIT, double idleIT, double errorIT) {
		super();
		this.runIT = runIT;
		this.idleIT = idleIT;
		this.errorIT = errorIT;
	}

	public double getRunIT() {
		return runIT;
	}

	public void setRunIT(double runIT) {
		this.runIT = runIT;
	}

	public double getIdleIT() {
		return idleIT;
	}

	public void setIdleIT(double idleIT) {
		this.idleIT = idleIT;
	}

	public double getErrorIT() {
		return errorIT;
	}

	public void setErrorIT(double errorIT) {
		this.errorIT = errorIT;
	}
	
	
}
