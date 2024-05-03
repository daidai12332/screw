package com.example.screw.vo;

public class EquipmentDataAvgObj {

	// ��Ʋέp���e obj: ���A��ҡB�����q�y�B�}�v
	private int runRatio;     // �B�@�����
	
	private int idleRatio;    // ���m�����
	
	private int errorRaio;    // ���~�����
	
	private int currentAvg;   // �@�Ѫ������q�y�AT �Τ@�Ѫ���ƥh�p��
		
	private int passRatio;    // �}�v

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
