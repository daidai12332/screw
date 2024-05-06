package com.example.screw.vo;

// 用來對應 settle dao 的回傳結果
public class SettleOrderVo {
	
	private String orderNo;
	
	private double currentSum;      // 電流量加總
	
	private long produceTodaySum;    // pass加總

	public SettleOrderVo() {
		super();
	}

	public SettleOrderVo(String orderNo, double currentSum, long produceTodaySum) {
		super();
		this.orderNo = orderNo;
		this.currentSum = currentSum;
		this.produceTodaySum = produceTodaySum;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public double getCurrentSum() {
		return currentSum;
	}

	public void setCurrentSum(double currentSum) {
		this.currentSum = currentSum;
	}

	public long getProduceTodaySum() {
		return produceTodaySum;
	}

	public void setProduceTodaySum(long produceTodaySum) {
		this.produceTodaySum = produceTodaySum;
	}

}
