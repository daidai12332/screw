package com.example.screw;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.Assert;

import com.example.screw.constants.MaterialCarbonCoefficient;
import com.example.screw.entity.ReceiveData;
import com.example.screw.entity.Order;
import com.example.screw.repository.OrderDao;
import com.example.screw.service.ifs.OrderService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.CalculateInformationRes;
import com.example.screw.vo.PowerUpdateRes;
import com.example.screw.vo.ProduceObj;
import com.example.screw.vo.SearchOrderRes;
import com.example.screw.vo.SettleOrderVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class OrderServiceTest {

	@Autowired
	private OrderService screwMaterialService;

	@Autowired
	private OrderDao screwMaterialDao;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void daoTest() throws JsonProcessingException {
//		// �R�������q��
//		int res1 = screwMaterialDao.deleteOrder("1");
//		System.out.println(res1);
//		// �[�`�Q��@��Ѫ��שM���q
//		List<SettleOrderVo> res2 = screwMaterialDao.sumOrderKWhAndPass(LocalDate.now().minusDays(1).atTime(0, 0, 0), LocalDate.now().minusDays(1).atTime(23, 59, 59, 999999999), "%%");	
//		System.out.println(mapper.writeValueAsString(res2));
//		// ������Ѧ��Ͳ��� material �����
//		List<ScrewMaterial> res3 = screwMaterialDao.selectScrewMaterialToday(LocalDate.now().atTime(0, 0, 0), LocalDateTime.now());
//		System.out.println(mapper.writeValueAsString(res3));
//		// ����Ҧ��q��
//		 List<ScrewMaterial> res4 = screwMaterialDao.searchOrder("", "");
//		System.out.println(mapper.writeValueAsString(res4));
	}
	
	// �s�W�渹
	@Test
	void addOrderTest() throws JsonProcessingException {
		String orderNumber = "1";
		String name = "���ÿ�304 �j���Y ��������� #8-32X1";
		int aim = 10000;
		int weight = 500;
		List<ProduceObj> raw = new ArrayList<>(List.of(new ProduceObj("�y�ƽu��", 1.01, 2.3883)));
		List<ProduceObj> process = new ArrayList<>(List.of(new ProduceObj("�q�O", 0, 0)));
		
		/*********** ���զ��\ ***********/
		BaseRes res = screwMaterialService.createOrder(orderNumber, name, aim, weight, raw, process);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 400, "Add Test Fail");
		
		/*********** ���խ��� ***********/
		res = screwMaterialService.createOrder(orderNumber, name, aim, weight, raw, process);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 400, "Add Test Fail");
	}
	
	// �s��渹
	@Test
	void editOrderTest() throws JsonProcessingException {
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

	// �j�M�渹
	@Test
	void searchOrderTest() throws JsonProcessingException {
		/*********** ���զ��\ ***********/
		SearchOrderRes res = screwMaterialService.searchOrder("2", null);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 200, "First Test Fail");
		
		res = screwMaterialService.searchOrder("1", null);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 200, "Second Test Fail");
		
		res = screwMaterialService.searchOrder(null, null);
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 200, "Third Test Fail");

		res = screwMaterialService.searchOrder(null, "��");
		System.out.println(mapper.writeValueAsString(res));
		Assert.isTrue(res.getCode() == 200, "Fourth Test Fail");

	}
	
	// �C�鵲��U�渹�ֿn�ӹq�q�M�Ͳ��q(Schedule)
	@Test
	void settleOrderPowerTest() {
		/*********** ���զ��\ ***********/
		screwMaterialService.settleOrderPower();
	}
	
	// �p��ұ�(�Ω�ұƧe�{): �Ѽ�(null) �Ǧ^(�C���渹����Ʈw��ơB�ұƭ�)
	@Test
	void calculateCarbonEmission() throws JsonProcessingException {
		/*********** ���զ��\ ***********/
		CalculateInformationRes res = screwMaterialService.calculateCarbonEmission();
		System.out.println(mapper.writeValueAsString(res));
	}
	
	// ��s�q�O�ϥζq(�Ω�ұƲӸ`�e�{): �Ѽ�(���b�d�ݪ��渹, �ؼжq, �ثe���q, �������q, �L�h�ֿn�ιq��) �Ǧ^(�̷s�C���������ϥιq��)
	@Test
	void powerUpdateTest() throws JsonProcessingException {
		/*********** ���զ��\ ***********/
		PowerUpdateRes res = screwMaterialService.powerUpdate("1", 10000, 0, 600, 0.80);
		System.out.println(mapper.writeValueAsString(res));
	}
}
