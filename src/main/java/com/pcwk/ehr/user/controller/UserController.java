package com.pcwk.ehr.user.controller;

import java.lang.ProcessBuilder.Redirect;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.email.dao.EmailDao;
import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Controller
public class UserController {

	final Logger log = LogManager.getLogger(getClass());

	@Qualifier("userServiceImpl")
	@Autowired
	private UserService userService;

	@Autowired
	private EmailDao emailDao;

	public UserController() {
		super();
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│    **UserController() 생성**           │");
		log.debug("└───────────────────────────────────────┘");

	}

	@RequestMapping(value = "/user/doSave.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(HttpServletRequest req, UserVO param) throws SQLException {

		String jsonString = "";
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│    **doSave() 실행**                   │" + param);
		log.debug("└───────────────────────────────────────┘");

//		UserVO inVO = new UserVO();
//		String userId = req.getParameter("userId");
//		String name = req.getParameter("name");
//		String password = req.getParameter("password");
//		String regDt = req.getParameter("regDt");
//		String login = req.getParameter("login");
//		String recommend = req.getParameter("recommend");
//		String grade = req.getParameter("grade");
//		String email = req.getParameter("email");
//
//		inVO.setUserId(userId);
//		inVO.setName(name);
//		inVO.setPassword(password);
//		inVO.setLogin(Integer.parseInt(login));
//		inVO.setRecommend(Integer.parseInt(recommend));
//
//		inVO.setGrade(Level.valueOf(Integer.parseInt(grade)));
//
//		inVO.setEmail(email);
//
//		log.debug(" | inVO        | " + inVO);

		emailDao.doSave(param.getEmail());
		int flag = userService.doSave(param);

		String message = "";

		if (flag == 1) {
			message = param.getName() + "님 등록 성공하였습니다.";
		} else {
			message = param.getName() + "님 등록 실패하였습니다";
		}

		MessageVO messageVO = new MessageVO(flag, message);

		jsonString = new Gson().toJson(messageVO);
		log.debug("jsonString : \n {}" + jsonString);

		return jsonString;

	}

	@RequestMapping(value = "/user/doDelete.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(HttpServletRequest req) throws SQLException {
		String jsonString = "";

		log.debug("┌───────────────────────────────────────┐");
		log.debug("│          **doDelete() 실행**           │");
		log.debug("└───────────────────────────────────────┘");

		UserVO inVO = new UserVO();

		String userEmail = req.getParameter("email");
		inVO.setEmail(userEmail);
		log.debug("inVO: {}", inVO);

		int flag = userService.doDelete(inVO);
		int flag2 = emailDao.doDelete(inVO);

		String message = "";

		if (flag == 1 && flag2 == 1) {
			message = inVO.getEmail() + "님이 삭제 되었습니다.";
		} else {
			message = inVO.getEmail() + "님을 삭제 실패 했습니다.";
		}

		jsonString = new Gson().toJson(new MessageVO(flag, message));
		log.debug("josnString : \n {}", jsonString);

		return jsonString;

	}

	@RequestMapping(value = "/user/doUpdate.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(HttpServletRequest req, UserVO param) throws SQLException {
		String jsonString = "";
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│      **doUpdate() 실행 **                                │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		int flag2 = emailDao.doUpdate_Email(param);
		param.changeEmail();
		int flag = userService.doUpdate(param);

		String message = "";

		if (flag == 1) {
			message = param.getName() + "님 수정 성공하였습니다.";
		} else {
			message = param.getName() + "님 수정 실패하였습니다";
		}

		MessageVO messageVO = new MessageVO(flag, message);

		jsonString = new Gson().toJson(messageVO);
		log.debug("jsonString : \n {}" + jsonString);

		return jsonString;

	}

	// 회원가입
	@RequestMapping(value = "/registerSave.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String RegisterSave(@RequestBody UserVO param) throws SQLException {

		log.debug("접근!!");

		String message = "";
		int flag_email;
		int flag_phone;
		int flag_doSave;
		EmailVO emailVO;

		// 1. 이메일 체크
		flag_email = userService.isExistsEmail(param.getEmail());
		if (flag_email != 0) {
			message = "이미 사용 중인 이메일 입니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 2.1 인증번호 검색
		try {
			emailVO = emailDao.doSelectOne(new EmailVO(param.getEmail()));
		} catch (NullPointerException e) {
			message = "인증 코드를 획득하세요.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 2.2 인증번호 검증
		if (!param.getAuth_code().equals(emailVO.getAuth_code())) {
			message = "인증 코드가 올바르지 않습니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 3. 전화 번호 체크
		flag_phone = userService.isExistsPhone(param.getPhone());
		if (flag_phone != 0) {
			message = "이미 사용 중인 전화번호 입니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 4. 회원 등록
		flag_doSave = userService.doSave(param);
		if (flag_doSave == 1) {
			message = "회원가입이 완료되었습니다.";
		} else {
			message = "회원가입 중 에러가 발생했습니다.";
		}

		return new Gson().toJson(new MessageVO(flag_doSave, message));
	}

	// 로그인
	@RequestMapping(value = "/doLogin.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String doLogin(@RequestBody UserVO param, HttpServletRequest req) throws SQLException {
		
		UserVO user;
		EmailVO emailVO;
		String message;

		if (userService.isExistsEmail(param.getEmail()) != 1) {
			return new Gson().toJson(new MessageVO(0, "존재하지 않는 이메일 입니다."));
		}

		// 2.1 인증번호 검색
		try {
			emailVO = emailDao.doSelectOne(new EmailVO(param.getEmail()));
		} catch (NullPointerException e) {
			message = "인증 코드를 획득하세요.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 2.2 인증번호 검증
		if (!param.getAuth_code().equals(emailVO.getAuth_code())) {
			message = "인증 코드가 올바르지 않습니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}
		
		try {
			
			user = userService.doSelectOne(param);
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			
		} catch (NullPointerException e) {
			return new Gson().toJson(new MessageVO(0, "회원 조회 중 오류가 발생하였습니다."));
		}

		return new Gson().toJson(new MessageVO(1, "로그인 성공"));
	}

}
