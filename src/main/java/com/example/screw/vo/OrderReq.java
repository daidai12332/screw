package com.example.screw.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.example.screw.entity.Order;

public class OrderReq extends Order{

	@NotBlank(message = "原料消耗不能為空")
	private List<ProduceObj> rawObj;

	@NotBlank(message = "製程消耗不能為空")
	private List<ProduceObj> produceObj;

	public OrderReq() {
		super();
	}

	public OrderReq(String orderNumber, String name, int weight, List<ProduceObj> rawObj, List<ProduceObj> produceObj) {
		super(orderNumber, name, weight, null, null);
		this.rawObj = rawObj;
		this.produceObj = produceObj;
	}

	public List<ProduceObj> getRawObj() {
		return rawObj;
	}

	public void setRawObj(List<ProduceObj> rawObj) {
		this.rawObj = rawObj;
	}

	public List<ProduceObj> getProduceObj() {
		return produceObj;
	}

	public void setProduceObj(List<ProduceObj> produceObj) {
		this.produceObj = produceObj;
	}

}
