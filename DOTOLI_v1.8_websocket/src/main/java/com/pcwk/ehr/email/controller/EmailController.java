package com.pcwk.ehr.email.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.email.service.EmailService;
import com.pcwk.ehr.user.service.UserService;

@Controller
public class EmailController {

	final Logger log = LogManager.getLogger(getClass());

	@Qualifier("emailServiceImpl")
	@Autowired
	private EmailService emailService;

	@Qualifier("userServiceImpl")
	@Autowired
	private UserService userService;

	// 회원가입_이메일 중복체크 및 인증번호 발송
	@RequestMapping(value = "/send_auth/register.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sendAuthCode(@RequestBody EmailVO param) throws Exception {

		String message = "";
		int flag = userService.isExistsEmail(param.getEmail());
		int flag2 = emailService.isExistsEmail(param);

		

		if (flag == 0 && flag2 == 0) {
			emailService.doSave(param.getEmail());
			emailService.send_Auth_Code(param);
			message = "인증 번호가 발송되었습니다.";
		} else if (flag == 0 && flag2 == 1) {
			emailService.send_Auth_Code(param);
			message = "인증 번호가 발송되었습니다.";
		} else {
			message = "이미 사용 중인 이메일 입니다.";
		}

		log.debug("flag2 : {}", flag2);
		log.debug("flag : {}",flag);
		log.debug(message);
		return new Gson().toJson(new MessageVO(flag, message));
	}
	
	// 로그인_이메일 중복체크 및 인증번호 발송
	@RequestMapping(value = "/send_auth/login.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sendAuthCode_Login(@RequestBody EmailVO param) throws Exception {

		String message = "";
		int flag = userService.isExistsEmail(param.getEmail());
		
		if(flag == 1) {
			emailService.send_Auth_Code(param);
			message = "인증 번호가 발송되었습니다.";
		} else {
			message = "존재하지 않는 이메일 입니다. 회원 가입을 진행해주세요.";
		}

		log.debug(flag);
		log.debug(message);
		return new Gson().toJson(new MessageVO(flag, message));
	}

}
