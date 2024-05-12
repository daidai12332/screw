package com.example.screw.vo;

import java.util.List;

public class ScrewMaterialObj {

	private String orderNumber; // 單號作為 pk，假設一張單號為一種螺絲

	private String name; // 使用者自行取的名稱

	private int aim; // 訂購的螺絲總量

	private int weight; // 單顆螺絲的重量(g)

	private List<ProduceObj> raw; // List<ProduceObj> 轉成 string = [{原料名稱 + 用量 + 碳排放係數},{}]

	private List<ProduceObj> process; // List<ProduceObj> 轉成 string = [{使用物/排放物 + 使用量/排放量 + 碳排放係數},{}]
	
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
