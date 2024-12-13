package com.pcwk.ehr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
	
	@RequestMapping(value = "/hello.do", method = RequestMethod.GET)
	public String Hello(Model model) {
		System.out.println("Hello, Spring MVC!!");
		
		model.addAttribute("message", "Hello, Spring MVC!");
		
		return "hello";
	}

}
