package com.example.screw.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.screw.entity.Order;
import com.example.screw.service.ifs.OrderService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.OrderManagementRes;
import com.example.screw.vo.SearchOrderRes;

@Validated
@CrossOrigin
@RestController
public class OrderServiceController {

	@Autowired
	private OrderService orderService;
	
	// 

	// �Բӥ\��

	// ���o�Ҧ��渹��������T
	@GetMapping(value = "orderManagement/find_order_all")
	public OrderManagementRes findOrderAll() {
		return orderService.findOrderAll();
	}
	
	//
	
	// ��x
	// �ާ@�\��

	// �s�W�渹�s�y��T
	@PostMapping(value = "order/create")
	public BaseRes createOrder(@Valid @RequestBody Order orderReq) {
		return orderService.createOrder(orderReq.getOrderNumber(), orderReq.getAim(), orderReq.getWeight(), orderReq.getStartProcessIndex(), orderReq.getEndProcessIndex(), orderReq.getPullThreadString(), orderReq.getFormingString(), orderReq.getGrindTeethString(), orderReq.getHeatTreatmentString(), orderReq.getElectroplatingString());
	}

	// �s��渹�s�y��T
	@PostMapping(value = "order/edit")
	public BaseRes editOrder(@Valid @RequestBody Order orderReq) {
		return orderService.editOrder(orderReq.getOrderNumber(), orderReq.getAim(), orderReq.getWeight(), orderReq.getStartProcessIndex(), orderReq.getEndProcessIndex(), orderReq.getPullThreadString(), orderReq.getFormingString(), orderReq.getGrindTeethString(), orderReq.getHeatTreatmentString(), orderReq.getElectroplatingString());
	}

	// �R���渹�s�y��T
	@PostMapping(value = "order/delete")
	public BaseRes deleteOrder(@RequestParam @NotEmpty(message = "�渹���i����") String orderNumber) {
		return orderService.deleteOrder(orderNumber);
	}

	// �j�M�渹�s�y��T
	@PostMapping(value = "order/get_order_manufacture")
	public SearchOrderRes getOrderManufacture(@RequestParam String orderNumber) {
		return orderService.getOrderManufactureByOrderNumber(orderNumber);
	}

}
