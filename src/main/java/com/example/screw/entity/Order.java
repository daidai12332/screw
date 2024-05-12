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

	@NotBlank(message = "單號不能為空")
	@Id
	@Column(name = "order_number")
	private String orderNumber; // 單號作為 pk，假設一張單號為一種螺絲

	@Column(name = "name")
	private String name; // 使用者自行取的名稱

	@Min(value = 1, message = "訂購數量需至少為 {value} 顆")
	@Column(name = "aim")
	private int aim; // 訂購的螺絲總量

	@Column(name = "produce")
	private int produce; // 已生產的螺絲數量

	@Min(value = 1, message = "單顆產品重量需至少為 {value} 克")
	@Column(name = "weight")
	private int weight; // 單顆螺絲的重量(g)

	@Column(name = "raw")
	private String raw; // List<ProduceObj> 轉成 string = [{原料名稱 + 用量 + 碳排放係數},{}]

	@Column(name = "process")
	private String process; // List<ProduceObj> 轉成 string = [{使用物/排放物 + 使用量/排放量 + 碳排放係數},{}]

	@Column(name = "del")
	private boolean del;

	public Order() {
		super();
	}

	public Order(String orderNumber, String name, int weight, String raw, String process) {
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

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}
	
}