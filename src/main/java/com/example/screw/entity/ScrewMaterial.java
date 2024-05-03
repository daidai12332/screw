package com.example.screw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "screw_material")
public class ScrewMaterial {

	@Id
	@Column(name = "order_no")
	private int orderNo;      // �渹�@�� pk�A���]�@�i�渹���@������
	
	@Column(name = "name")
	private String name;      // �ϥΪ̦ۦ�����W��

	@Column(name = "aim")
	private int aim;          // �q�ʪ������`�q
	
	@Column(name = "weight")
	private int weight;       // �������������q(g)
	
	@Column(name = "raw")
	private String raw;       // List<RawObj> �ন string = [{��ƦW�� + �ζq + scope1 �ұƩ�Y�� + scope 2 �ұƩ�Y��},{}]
	
	@Column(name = "process")
	private String process;   // List<ProcessObj> �ন string = [{�ϥΪ�/�Ʃ� + �ϥζq/�Ʃ�q},{}]

	public ScrewMaterial() {
		super();
	}

	public ScrewMaterial(int orderNo, String name, int weight, String raw, String process) {
		super();
		this.orderNo = orderNo;
		this.name = name;
		this.weight = weight;
		this.raw = raw;
		this.process = process;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}