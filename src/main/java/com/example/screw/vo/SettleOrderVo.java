package com.example.screw.vo;

// �Ψӹ��� settle dao ���^�ǵ��G
public class SettleOrderVo {
	
	private int orderNo;
	
	private double currentSum;      // �q�y�q�[�`
	
	private int produceTodaySum;    // pass�[�`

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
