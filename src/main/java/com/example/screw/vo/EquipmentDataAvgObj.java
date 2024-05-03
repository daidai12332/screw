package com.example.screw.vo;

public class EquipmentDataAvgObj {

	// 資料統計內容 obj: 狀態比例、平均電流、良率
	private int runRatio;     // 運作的比例
	
	private int idleRatio;    // 閒置的比例
	
	private int errorRaio;    // 錯誤的比例
	
	private int currentAvg;   // 一天的平均電流，T 用一天的秒數去計算
		
	private int passRatio;    // 良率

	public EquipmentDataAvgObj() {
		super();
	}

	public EquipmentDataAvgObj(int runRatio, int idleRatio, int errorRaio, int currentAvg, int passRatio) {
		super();
		this.runRatio = runRatio;
		this.idleRatio = idleRatio;
		this.errorRaio = errorRaio;
		this.currentAvg = currentAvg;
		this.passRatio = passRatio;
	}

	public int getRunRatio() {
		return runRatio;
	}

	public void setRunRatio(int runRatio) {
		this.runRatio = runRatio;
	}

	public int getIdleRatio() {
		return idleRatio;
	}

	public void setIdleRatio(int idleRatio) {
		this.idleRatio = idleRatio;
	}

	public int getErrorRaio() {
		return errorRaio;
	}

	public void setErrorRaio(int errorRaio) {
		this.errorRaio = errorRaio;
	}

	public int getCurrentAvg() {
		return currentAvg;
	}

	public void setCurrentAvg(int currentAvg) {
		this.currentAvg = currentAvg;
	}

	public int getPassRatio() {
		return passRatio;
	}

	public void setPassRatio(int passRatio) {
		this.passRatio = passRatio;
	}
		
}
