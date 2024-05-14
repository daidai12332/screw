package com.example.screw.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.screw.entity.Order;

public class OrderReq extends Order{

	@Valid
	@NotNull(message = "��Ʈ��Ӥ��ର��")
	private List<ProduceObj> rawObj;

	@Valid
	@NotNull(message = "�s�{���Ӥ��ର��")
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
