package com.example.screw;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.screw.repository.ScrewMaterialDao;
import com.example.screw.service.ifs.ScrewMaterialService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.ProduceObj;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class ScrewMaterialServiceTest {

	@Autowired
	private ScrewMaterialService screwMaterialService;

	@Autowired
	private ScrewMaterialDao screwMaterialDao;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void addOrderTest() throws JsonProcessingException {
		String orderNumber = "1";
		String name = "���ÿ�304 �j���Y ��������� #8-32X1";
		int aim = 10000;
		int weight = 500;
		List<ProduceObj> raw = new ArrayList<>(List.of(new ProduceObj("�y�ƽu��", 1.01, 2.3883)));
		List<ProduceObj> process = new ArrayList<>(List.of(new ProduceObj("�q�O", 0, 0)));
		
		/*********** ���զ��\ ***********/
		BaseRes res = screwMaterialService.addOrder(orderNumber, name, aim, weight, raw, process);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 400, "Add Test Fail");
		
		/*********** ���խ��� ***********/
		res = screwMaterialService.addOrder(orderNumber, name, aim, weight, raw, process);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 400, "Add Test Fail");
	}
	
	@Test
	void editOrder() throws JsonProcessingException {
		String orderNumber = "1";
		String name = "���ÿ�304 �j���Y ��������� #8-32X1";
		int aim = 10000;
		int weight = 600;
		List<ProduceObj> raw = new ArrayList<>(List.of(new ProduceObj("�y�ƽu��", 1.01, 2.3883)));
		List<ProduceObj> process = new ArrayList<>(List.of(new ProduceObj("�q�O", 0, 0)));

		/*********** ���ո�Ƥ��s�b ***********/
		BaseRes res = screwMaterialService.editOrder("2", name, aim, weight, raw, process);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 400, "Edit Not Exist Test Fail");

		/*********** ���զ��\ ***********/
		res = screwMaterialService.editOrder(orderNumber, name, aim, weight, raw, process);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 200, "Edit Success Test Fail");

	}
	
}
