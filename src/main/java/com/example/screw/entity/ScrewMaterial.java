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

	@NotBlank(message = "單號不能為空")
	@Id
	@Column(name = "order_number")
	private String orderNumber; // 單號作為 pk，假設一張單號為一種螺絲

	@Column(name = "name")
	private String name; // 使用者自行取的名稱

	@NotBlank(message = "訂購數量不能為空")
	@Min(value = 1, message = "訂購數量需至少為 {value} 顆")
	@Column(name = "aim")
	private int aim; // 訂購的螺絲總量

	@Column(name = "produce")
	private int produce; // 已生產的螺絲數量

	@NotBlank(message = "單顆產品重量不能為空")
	@Min(value = 1, message = "單顆產品重量需至少為 {value} 克")
	@Column(name = "weight")
	private int weight; // 單顆螺絲的重量(g)

	@NotBlank(message = "產品的原料不能為空")
	@Column(name = "raw")
	private String raw; // List<RawObj> 轉成 string = [{原料名稱 + 用量 + scope1 碳排放係數 + scope 2 碳排放係數},{}]

	@NotBlank(message = "產品的製程消耗不能為空")
	@Column(name = "process")
	private String process; // List<ProcessObj> 轉成 string = [{使用物/排放物 + 使用量/排放量},{}]

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