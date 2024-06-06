package com.example.screw.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.screw.constants.RtnCode;
import com.example.screw.entity.Order;
import com.example.screw.repository.OrderDao;
import com.example.screw.repository.OrderManagementDao;
import com.example.screw.service.ifs.OrderService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.OrderManagementRes;
import com.example.screw.vo.SearchOrderRes;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderManagementDao orderManagementDao;

	// �s�W�渹
	@Override
	public BaseRes createOrder(String orderNumber, int aim, int weight, int startProcessIndex, int endProcessIndex, String pullThread, String forming, String grindTeeth, String heatTreatment, String electroplating) {
			// �������J�A�p�G�w�s�b�|�^�� 0
			if (orderDao.insertOrder(orderNumber, aim, weight, startProcessIndex, endProcessIndex, pullThread, forming, grindTeeth, heatTreatment, electroplating) == 0) {
				return new BaseRes(RtnCode.ORDER_NUMBER_DUPLICATED.getCode(), RtnCode.ORDER_NUMBER_DUPLICATED.getMessage());
			}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// �s��渹
	@Override
	public BaseRes editOrder(String orderNumber, int aim, int weight, int startProcessIndex, int endProcessIndex,
			String pullThread, String forming, String grindTeeth, String heatTreatment, String electroplating) {
		if (orderDao.updateOrder(orderNumber, aim, weight, startProcessIndex, endProcessIndex, pullThread, forming, grindTeeth, heatTreatment, electroplating) == 0) {
			return new BaseRes(RtnCode.ORDER_NUMBER_NOT_EXISTS.getCode(), RtnCode.ORDER_NUMBER_NOT_EXISTS.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// �R���渹
	@Override
	public BaseRes deleteOrder(String orderNumber) {
		int res = orderDao.deleteOrder(orderNumber);
		if (res == 0) {
			return new BaseRes(RtnCode.ORDER_NUMBER_NOT_EXISTS.getCode(), RtnCode.ORDER_NUMBER_NOT_EXISTS.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	// �j�M�渹
	@Override
	public SearchOrderRes getOrderManufactureByOrderNumber(String orderNumber) {
		if (!StringUtils.hasText(orderNumber)) {
			return new SearchOrderRes(RtnCode.ORDER_NAME_CANNOT_BE_NULL.getCode(), RtnCode.ORDER_NAME_CANNOT_BE_NULL.getMessage(), null);
		}
		// temp ���s��j�M���^�ǭ�
		Order daoRes = orderDao.searchOrder(orderNumber);
		if (daoRes == null) {
			return new SearchOrderRes(RtnCode.NO_DATA.getCode(), RtnCode.NO_DATA.getMessage(), null);
		}
		return new SearchOrderRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), daoRes);
	}

	// �j�M�Ҧ��渹���򥻸��
	@Override
	public OrderManagementRes findOrderAll() {

		return new OrderManagementRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
				orderManagementDao.getAllOrder());
	}
}
