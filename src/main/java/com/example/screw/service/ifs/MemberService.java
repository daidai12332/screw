package com.example.screw.service.ifs;

import com.example.screw.vo.BaseRes;

public interface MemberService {
	
	// ���U
	public BaseRes signUp(String account, String pwd);

	// �n�J
	public BaseRes login(String account, String pwd);

	
}
