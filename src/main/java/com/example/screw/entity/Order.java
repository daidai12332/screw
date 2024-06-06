package com.example.screw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "order")
public class Order {

	@NotBlank(message = "�渹���ର��")
	@Id
	@Column(name = "order_number")
	private String orderNumber; // �渹�@�� pk�A���]�@�i�渹���@������

	@Min(value = 1, message = "�ؼХͲ��q�ݦܤ֬� {value} ��")
	@Column(name = "aim")
	private int aim; // �������������q(g)	
	
	@Min(value = 1, message = "�������~���q�ݦܤ֬� {value} �J")
	@Column(name = "weight")
	private int weight; // �������������q(g)

	@Min(value = 0, message = "�s�y���_�I�ܤֶ�����u")
	@Min(value = 4, message = "�s�y���_�I�ܤֶ����q��")
	@Column(name = "start_process_index")
	private int startProcessIndex;    // �s�y���_�I
	
	@Min(value = 0, message = "�s�y�����I�ܤֶ�����u")
	@Min(value = 4, message = "�s�y���_�I�ܤֶ����q��")
	@Column(name = "end_process_index")
	private int endProcessIndex;      // �s�y�����I
	
	@Column(name = "pull_thread")
	private String pullThreadString;  // ��u(0)
	
	@Column(name = "forming")
	private String formingString;     // ��y����(1)
	
	@Column(name = "grind_teeth")
	private String grindTeethString;     // �Ӥ�(2)

	@Column(name = "heat_treatment")
	private String heatTreatmentString;  // ���B�z(3)

	@Column(name = "electroplating")
	private String electroplatingString; // �q��(4)

	@Column(name = "del")
	private boolean del;

	public Order() {
		super();
	}
	
	public Order(@NotBlank(message = "�渹���ର��") String orderNumber,
			@Min(value = 1, message = "�ؼХͲ��q�ݦܤ֬� {value} ��") int aim,
			@Min(value = 1, message = "�������~���q�ݦܤ֬� {value} �J") int weight,
			@Min(value = 0, message = "�s�y���_�I�ܤֶ�����u") @Min(value = 4, message = "�s�y���_�I�ܤֶ����q��") int startProcessIndex,
			@Min(value = 0, message = "�s�y�����I�ܤֶ�����u") @Min(value = 4, message = "�s�y���_�I�ܤֶ����q��") int endProcessIndex,
			String pullThreadString, String formingString, String grindTeethString, String heatTreatmentString,
			String electroplatingString, boolean del) {
		super();
		this.orderNumber = orderNumber;
		this.aim = aim;
		this.weight = weight;
		this.startProcessIndex = startProcessIndex;
		this.endProcessIndex = endProcessIndex;
		this.pullThreadString = pullThreadString;
		this.formingString = formingString;
		this.grindTeethString = grindTeethString;
		this.heatTreatmentString = heatTreatmentString;
		this.electroplatingString = electroplatingString;
		this.del = del;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public int getAim() {
		return aim;
	}

	public void setAim(int aim) {
		this.aim = aim;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getStartProcessIndex() {
		return startProcessIndex;
	}

	public void setStartProcessIndex(int startProcessIndex) {
		this.startProcessIndex = startProcessIndex;
	}

	public int getEndProcessIndex() {
		return endProcessIndex;
	}

	public void setEndProcessIndex(int endProcessIndex) {
		this.endProcessIndex = endProcessIndex;
	}

	public String getPullThreadString() {
		return pullThreadString;
	}

	public void setPullThreadString(String pullThreadString) {
		this.pullThreadString = pullThreadString;
	}

	public String getFormingString() {
		return formingString;
	}

	public void setFormingString(String formingString) {
		this.formingString = formingString;
	}

	public String getGrindTeethString() {
		return grindTeethString;
	}

	public void setGrindTeethString(String grindTeethString) {
		this.grindTeethString = grindTeethString;
	}

	public String getHeatTreatmentString() {
		return heatTreatmentString;
	}

	public void setHeatTreatmentString(String heatTreatmentString) {
		this.heatTreatmentString = heatTreatmentString;
	}

	public String getElectroplatingString() {
		return electroplatingString;
	}

	public void setElectroplatingString(String electroplatingString) {
		this.electroplatingString = electroplatingString;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

}