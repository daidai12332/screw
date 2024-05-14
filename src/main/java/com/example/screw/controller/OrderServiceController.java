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

	/**** �H�U���e�x�\�� ****/
	
	// �p��ұ�(�Ω�ұƧe�{): �Ѽ�(null) �Ǧ^(�C���渹����Ʈw��ơB�ұƭ�)
	@GetMapping(value = "forestage/search")
	public CalculateInformationRes calculateCarbonEmission() {
		return orderService.calculateCarbonEmission();
	}

	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹, �ؼжq, �ثe���q, �������q, �L�h�ֿn�ιq��) �Ǧ^(�̷s�C���������ϥιq��)
	@PostMapping(value = "forestage/power_update")
	public PowerUpdateRes powerUpdate(@Valid @RequestBody PowerUpdateReq powerUpdateReq) {
		return orderService.powerUpdate(powerUpdateReq.getOrderNumber(), powerUpdateReq.getAim(), powerUpdateReq.getProduce(), powerUpdateReq.getWeight(), powerUpdateReq.getPowerUsage());	
	}
}
