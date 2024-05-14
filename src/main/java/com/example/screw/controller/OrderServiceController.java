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

	/**** 以下為前台功能 ****/
	
	// 計算碳排(用於碳排呈現): 參數(null) 傳回(每筆單號的資料庫資料、碳排值)
	@GetMapping(value = "forestage/search")
	public CalculateInformationRes calculateCarbonEmission() {
		return orderService.calculateCarbonEmission();
	}

	// 更新電力使用量(用於碳排細節呈現): 參數(正在查看的單號, 目標量, 目前產量, 單顆重量, 過去累積用電度) 傳回(最新每顆螺絲的使用電度)
	@PostMapping(value = "forestage/power_update")
	public PowerUpdateRes powerUpdate(@Valid @RequestBody PowerUpdateReq powerUpdateReq) {
		return orderService.powerUpdate(powerUpdateReq.getOrderNumber(), powerUpdateReq.getAim(), powerUpdateReq.getProduce(), powerUpdateReq.getWeight(), powerUpdateReq.getPowerUsage());	
	}
}
