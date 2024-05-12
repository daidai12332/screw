package com.example.screw.vo;



public class ReceiveDataLong {
	
	private String name;
	
	private double current;
	
	private long pass;
	
	private long ng;

	public ReceiveDataLong() {
		super();
	}

	public ReceiveDataLong(String name, double current, long pass, long ng) {
		super();
		this.name = name;
		this.current = current;
		this.pass = pass;
		this.ng = ng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public long getPass() {
		return pass;
	}

	public void setPass(long pass) {
		this.pass = pass;
	}

	public long getNg() {
		return ng;
	}

	public void setNg(long ng) {
		this.ng = ng;
	}
	
}
