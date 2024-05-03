package com.example.screw.vo;

public class EquipmentDataAvgObj {

	// 資料統計內容 obj: 狀態比例、平均電流、良率
	private double runRatio;     // 運作的比例
	
	private double idleRatio;    // 閒置的比例
	
	private double errorRaio;    // 錯誤的比例
	
	private double currentAvg;   // 一天的平均電流，T 用一天的秒數去計算
		
	private double passRatio;    // 良率

	public EquipmentDataAvgObj() {
		super();
	}

	public EquipmentDataAvgObj(double runRatio, double idleRatio, double errorRaio, double currentAvg,
			double passRatio) {
		super();
		this.runRatio = runRatio;
		this.idleRatio = idleRatio;
		this.errorRaio = errorRaio;
		this.currentAvg = currentAvg;
		this.passRatio = passRatio;
	}

	public double getRunRatio() {
		return runRatio;
	}

	public void setRunRatio(double runRatio) {
		this.runRatio = runRatio;
	}

	public double getIdleRatio() {
		return idleRatio;
	}

	public void setIdleRatio(double idleRatio) {
		this.idleRatio = idleRatio;
	}

	public double getErrorRaio() {
		return errorRaio;
	}

	public void setErrorRaio(double errorRaio) {
		this.errorRaio = errorRaio;
	}

	public double getCurrentAvg() {
		return currentAvg;
	}

	public void setCurrentAvg(double currentAvg) {
		this.currentAvg = currentAvg;
	}

	public double getPassRatio() {
		return passRatio;
	}

	public void setPassRatio(double passRatio) {
		this.passRatio = passRatio;
	}
		
}
