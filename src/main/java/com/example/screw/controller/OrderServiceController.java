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

import com.example.screw.service.ifs.OrderService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.OrderManagementRes;
import com.example.screw.vo.OrderReq;
import com.example.screw.vo.PowerUpdateReq;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.SearchOrderRes;
import com.example.screw.vo.SearchReq;

@Validated
@CrossOrigin
@RestController
public class OrderServiceController {

	@Autowired
	private OrderService orderService;

	// �s�W�渹
	@PostMapping(value = "order/create")
	public BaseRes createOrder(@Valid @RequestBody OrderReq orderReq) {
		return orderService.createOrder(orderReq.getOrderNumber(), orderReq.getName(), orderReq.getAim(), orderReq.getWeight(), orderReq.getRawObj(), orderReq.getProduceObj());
	}
	
	// �s��渹
	@PostMapping(value = "order/edit")
	public BaseRes editOrder(@Valid @RequestBody OrderReq orderReq) {
		return orderService.editOrder(orderReq.getOrderNumber(), orderReq.getName(), orderReq.getAim(), orderReq.getWeight(), orderReq.getRawObj(), orderReq.getProduceObj());
	}

	// �R���渹
	@PostMapping(value = "order/delete")
	public BaseRes deleteOrder(@RequestParam @NotEmpty(message = "�渹���i����") String orderNumber) {
		return orderService.deleteOrder(orderNumber);
	}
	
	// �j�M�渹
	@PostMapping(value = "order/search")
	public SearchOrderRes searchOrder(@RequestBody SearchReq searchReq) {
		return orderService.searchOrder(searchReq.getOrderNumber(), searchReq.getName());
	}
	
	/**** �H�U���e�x�\�� ****/
	
	// �p��ұ�(�Ω�ұƧe�{): �Ѽ�(null) �Ǧ^(�C���渹����Ʈw��ơB�ұƭ�)
//	@GetMapping(value = "forestage/search")
//	public CalculateInformationRes calculateCarbonEmission() {
//		return orderService.calculateCarbonEmission();
//	}

	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹, �ؼжq, �ثe���q, �������q, �L�h�ֿn�ιq��) �Ǧ^(�̷s�C���������ϥιq��)
//	@PostMapping(value = "forestage/power_update")
//	public PowerUpdateRes powerUpdate(@Valid @RequestBody PowerUpdateReq powerUpdateReq) {
//		return orderService.powerUpdate(powerUpdateReq.getOrderNumber(), powerUpdateReq.getAim(), powerUpdateReq.getProduce(), powerUpdateReq.getWeight(), powerUpdateReq.getPowerUsage());	
//	}
	
	// ���o�Ҧ��渹��������T
	@PostMapping(value = "orderManagement/findOrderAll")
	public OrderManagementRes findOrderAll() {
		return orderService.findOrderAll();
	}
}
