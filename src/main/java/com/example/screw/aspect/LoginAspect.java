package com.example.screw.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.screw.constants.RtnCode;
import com.example.screw.vo.BaseRes;


@Component
@Aspect
public class LoginAspect {
	
	// import slf4j
	private Logger log = LoggerFactory.getLogger(getClass());

	@Pointcut("execution (public * com.example.screw.controller.*.*(..)) &&"
			+ "!execution (public * com.example.screw.controller.*.login(..))")
	public void pointcut() { // �n���J����k

	}

	@Around("pointcut()")
	public Object loginCheck(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
	    // ���� () ������ƨ� log
		log.info("API" + signature.getName());   // �����I�s�� API �W��
		// ���o Request ����k�p�U�A���ӦL�H�A����ӶK�{���X�Y�i
		// ServletRequestAttributes �~����k .getRequest()
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String account = (String)session.getAttribute("account");  // �]���w�]�O obj�A�G�n�j���૬���r��
		if(!StringUtils.hasText(account)) {
			return new BaseRes(RtnCode.PLEASE_LOGIN_FIRST.getCode(), RtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}
		Object res = pjp.proceed();
		return res;	
	}

}
