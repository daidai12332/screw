package com.example.screw.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.screw.constants.RtnCode;
import com.example.screw.entity.Account;
import com.example.screw.service.ifs.MachineService;
import com.example.screw.service.ifs.MemberService;
import com.example.screw.service.ifs.OrderService;
import com.example.screw.vo.BaseRes;
import com.example.screw.vo.MachineNameReq;
import com.example.screw.vo.MachineNameRes;
import com.example.screw.vo.OrderReq;
import com.example.screw.vo.SearchOrderRes;
import com.example.screw.vo.SearchReq;

@CrossOrigin
@RestController
public class MemberServiceController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MachineService machineService;
	
	// ���U
	@PostMapping(value = "member/sign_up")
	public BaseRes signUp(@Valid @RequestBody Account account) {
		return memberService.signUp(account.getAccount(), account.getPwd());
	}

	// �n�J
	@PostMapping(value = "member/login")
	public BaseRes login(@Valid @RequestBody Account account, HttpSession httpSession) {
		BaseRes res = memberService.login(account.getAccount(), account.getPwd());
		if(res.getCode() != 200) {
			return res;
		}
		httpSession.setAttribute("account", account.getPwd());
		return res;
	}
	
	// �n�X
	@GetMapping(value = "member/logout")
	public BaseRes logout(HttpSession httpSession) {
		httpSession.removeAttribute("account");
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	/**** �H�U���渹�\�� ****/
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

	/**** �H�U���]�ƥ\�� ****/
	@PostMapping(value = "screw/findMachineName")
	public MachineNameRes findMachineName() {
		return machineService.findMachineName();
	}
	
	@PostMapping(value = "screw/deleteMachine")
	public BaseRes deleteMachine(@RequestBody MachineNameReq req) {
		return machineService.deleteMachine(req);
	}
	
	@PostMapping(value = "screw/addMachine")
	public BaseRes addMachine(@RequestParam(value = "name") String machineName) {
		return machineService.addMachine(machineName);
	}

}
