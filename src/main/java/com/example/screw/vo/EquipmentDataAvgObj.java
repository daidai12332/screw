package com.example.screw.vo;

public class EquipmentDataAvgObj {

	// ��Ʋέp���e obj: ���A��ҡB�����q�y�B�}�v
	private double runRatio;     // �B�@�����
	
	private double idleRatio;    // ���m�����
	
	private double errorRaio;    // ���~�����
	
	private double currentAvg;   // �@�Ѫ������q�y�AT �Τ@�Ѫ���ƥh�p��
		
	private double passRatio;    // �}�v

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
