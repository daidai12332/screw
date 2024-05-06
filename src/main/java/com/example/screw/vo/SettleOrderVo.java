package com.example.screw.vo;

// 用來對應 settle dao 的回傳結果
public class SettleOrderVo {
	
	private int orderNo;
	
	private double currentSum;      // 電流量加總
	
	private int produceTodaySum;    // pass加總

	public SettleOrderVo() {
		super();
	}

	public SettleOrderVo(int orderNo, double currentSum, int produceTodaySum) {
		super();
		this.orderNo = orderNo;
		this.currentSum = currentSum;
		this.produceTodaySum = produceTodaySum;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public double getCurrentSum() {
		return currentSum;
	}

	public void setCurrentSum(double currentSum) {
		this.currentSum = currentSum;
	}

	public int getProduceTodaySum() {
		return produceTodaySum;
	}

	public void setProduceTodaySum(int produceTodaySum) {
		this.produceTodaySum = produceTodaySum;
	}

}
