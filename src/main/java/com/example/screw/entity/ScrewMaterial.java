package com.example.screw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "screw_material")
public class ScrewMaterial {

	@NotBlank(message = "�渹���ର��")
	@Id
	@Column(name = "order_number")
	private String orderNumber; // �渹�@�� pk�A���]�@�i�渹���@������

	@Column(name = "name")
	private String name; // �ϥΪ̦ۦ�����W��

	@NotBlank(message = "�q�ʼƶq���ର��")
	@Min(value = 1, message = "�q�ʼƶq�ݦܤ֬� {value} ��")
	@Column(name = "aim")
	private int aim; // �q�ʪ������`�q

	@Column(name = "produce")
	private int produce; // �w�Ͳ��������ƶq

	@NotBlank(message = "�������~���q���ର��")
	@Min(value = 1, message = "�������~���q�ݦܤ֬� {value} �J")
	@Column(name = "weight")
	private int weight; // �������������q(g)

	@NotBlank(message = "���~����Ƥ��ର��")
	@Column(name = "raw")
	private String raw; // List<RawObj> �ন string = [{��ƦW�� + �ζq + scope1 �ұƩ�Y�� + scope 2 �ұƩ�Y��},{}]

	@NotBlank(message = "���~���s�{���Ӥ��ର��")
	@Column(name = "process")
	private String process; // List<ProcessObj> �ন string = [{�ϥΪ�/�Ʃ� + �ϥζq/�Ʃ�q},{}]

	@Column(name = "delete")
	private boolean delete;

	public ScrewMaterial() {
		super();
	}

	public ScrewMaterial(String orderNumber, String name, int weight, String raw, String process) {
		super();
		this.orderNumber = orderNumber;
		this.name = name;
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

	public int getProduce() {
		return produce;
	}

	public void setProduce(int produce) {
		this.produce = produce;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
}