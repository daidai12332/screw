package com.example.screw.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.screw.entity.Account;
import com.example.screw.service.ifs.MemberService;
import com.example.screw.vo.BaseRes;

@CrossOrigin
@RestController
public class MemberServiceController {

	@Autowired
	private MemberService memberService;
	
	// µù¥U
	@PostMapping(value = "member/sign_up")
	public BaseRes signUp(@Valid @RequestBody Account account) {
		return memberService.signUp(account.getAccount(), account.getPwd());
	}

	// µn¤J
	@PostMapping(value = "member/login")
	public BaseRes login(@Valid @RequestBody Account account, HttpSession httpSession) {
		BaseRes res = memberService.login(account.getAccount(), account.getPwd());
		if(res.getCode() != 200) {
			return res;
		}
		httpSession.setAttribute("account", account.getPwd());
		return res;
	}

	
}
