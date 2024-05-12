package com.example.screw.vo;

public class VoltageRes extends BaseRes{

	private double voltage;

	public VoltageRes() {
		super();
	}

	public VoltageRes(int code, String message) {
		super(code, message);
	}

	public VoltageRes(int code, String message, double voltage) {
		super(code, message);
		this.voltage = voltage;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	
}
