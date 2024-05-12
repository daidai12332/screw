package com.example.screw.vo;

import java.util.List;

public class ScrewMaterialObj {

	private String orderNumber; // �渹�@�� pk�A���]�@�i�渹���@������

	private String name; // �ϥΪ̦ۦ�����W��

	private int aim; // �q�ʪ������`�q

	private int weight; // �������������q(g)

	private List<ProduceObj> raw; // List<ProduceObj> �ন string = [{��ƦW�� + �ζq + �ұƩ�Y��},{}]

	private List<ProduceObj> process; // List<ProduceObj> �ন string = [{�ϥΪ�/�Ʃ� + �ϥζq/�Ʃ�q + �ұƩ�Y��},{}]
	
	public ScrewMaterialObj() {
		super();
	}

	public ScrewMaterialObj(String orderNumber, String name, int aim, int produce, int weight, List<ProduceObj> raw,
			List<ProduceObj> process) {
		super();
		this.orderNumber = orderNumber;
		this.name = name;
		this.aim = aim;
		this.weight = weight;
		this.raw = raw;
		this.process = process;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<ProduceObj> getRaw() {
		return raw;
	}

	public void setRaw(List<ProduceObj> raw) {
		this.raw = raw;
	}

	public List<ProduceObj> getProcess() {
		return process;
	}

	public void setProcess(List<ProduceObj> process) {
		this.process = process;
	}
}
