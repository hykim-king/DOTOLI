package com.pcwk.ehr.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;

@Controller
public class MainController {

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main() {

		return "/main";
	}


	@RequestMapping(value = "/user/login/login.do", method = RequestMethod.GET)
	public String login(HttpServletRequest req) {
		System.out.println("login");

		HttpSession session = req.getSession(false);

		if (session != null && session.getAttribute("user") != null) {

			return "redirect:/main.do";
		}

		return "/user/login/login";
	}

	@RequestMapping(value = "/user/login/logout.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String logout(HttpServletRequest req) {
		System.out.println("logout");

		HttpSession session = req.getSession(false);

		if (session.getAttribute("user") != null ) {
			session.invalidate();

			return new Gson().toJson(new MessageVO(1, "정상적으로 로그아웃 되었습니다."));

		}

		return new Gson().toJson(new MessageVO(0, "로그인 되지 않았습니다."));
	}

	@RequestMapping(value = "/user/login/register.do", method = RequestMethod.GET)
	public String register() {
		System.out.println("register");

		return "/user/login/register";
	}
	
	@RequestMapping(value = "/user/login/find_email.do", method = RequestMethod.GET)
	public String find_email() {
		System.out.println("find_email");

		return "/user/login/find_email";
	}
	
	@RequestMapping(value = "/user/mypage.do", method = RequestMethod.GET)
	public String myPage(HttpSession session) {
		System.out.println("mypage");

		if(session.getAttribute("user") == null) {
			return "redirect:/user/login/login.do";
		}

		return "/user/mypage/mypage";
	}
	
	@RequestMapping(value = "/product_board/post.do", method = RequestMethod.GET)
	public String product_BoardPost(HttpSession session) {
		System.out.println("post");

		if(session.getAttribute("user") == null) {
			return "redirect:/user/login/login.do";
		}

		return "/product_board/post";
	}
	
	

}