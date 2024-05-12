package com.example.screw.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.screw.constants.RtnCode;
import com.example.screw.entity.Account;
import com.example.screw.repository.AccountDao;
import com.example.screw.service.ifs.MemberService;
import com.example.screw.vo.BaseRes;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private AccountDao accountDao;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public BaseRes signUp(String account, String pwd) {
		if(accountDao.insertAccount(account, encoder.encode(pwd)) == 0) {
			return new BaseRes(RtnCode.ACCOUNT_EXISTS.getCode(), RtnCode.ACCOUNT_EXISTS.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}
	
	// �n�J
	@Override
	public BaseRes login(String account, String pwd) {
		// �T�{�b���O�_�s�b
		Optional<Account> op = accountDao.findById(account);
		if(op.isEmpty()) {
			return new BaseRes(RtnCode.ACCOUNT_NOT_EXISTS.getCode(), RtnCode.ACCOUNT_NOT_EXISTS.getMessage());
		}
		// �p�G�K�X�����T�A�^�ǿ��~
		if(!encoder.matches(pwd, op.get().getPwd())) {
			return new BaseRes(RtnCode.PASSWORD_ERROR.getCode(), RtnCode.PASSWORD_ERROR.getMessage());
		}
		// �b���M�K�X�����T
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}
	
	// �n�X
	
		
}
