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
	private int orderNo;      // 單號作為 pk，假設一張單號為一種螺絲
	
	@Column(name = "name")
	private String name;      // 使用者自行取的名稱

	@Column(name = "aim")
	private int aim;          // 訂購的螺絲總量
	
	@Column(name = "weight")
	private int weight;       // 單顆螺絲的重量(g)
	
	@Column(name = "raw")
	private String raw;       // List<RawObj> 轉成 string = [{原料名稱 + 用量 + scope1 碳排放係數 + scope 2 碳排放係數},{}]
	
	@Column(name = "process")
	private String process;   // List<ProcessObj> 轉成 string = [{使用物/排放物 + 使用量/排放量},{}]

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