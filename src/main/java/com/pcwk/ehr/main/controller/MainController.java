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

	@RequestMapping(value = "/user/index.do", method = RequestMethod.GET)
	public String index() {
		System.out.println("index");

		return "/user/index";
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

}