package com.example.screw.vo;

// �Ψӹ��� settle dao ���^�ǵ��G
public class SettleOrderVo {
	
	private String orderNo;
	
	private double currentSum;      // �q�y�q�[�`
	
	private long produceTodaySum;    // pass�[�`

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
