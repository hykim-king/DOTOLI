package com.pcwk.ehr.async;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;

@Controller
public class AsyncController {

	final Logger log = LogManager.getLogger(getClass());

	public AsyncController() {
		super();
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│ **AsyncController() 생성*              │");
		log.debug("└───────────────────────────────────────┘");
	}

	// --/async/async_index.do
	@GetMapping("/async/async_index.do")
	public String asyncIndex() {
		String viewName = "async/async_index";

		log.debug("┌───────────────────────────────────────┐");
		log.debug("│      **AsyncIndex() 실행*              │");
		log.debug("└───────────────────────────────────────┘");

		return viewName;
	}

	@RequestMapping(value = "/async/async_result.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String asyncResult(HttpServletRequest req) {
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│      **AsyncResult() 실행*              │");
		log.debug("└───────────────────────────────────────┘");
		String userName = req.getParameter("username");
		String passwd = req.getParameter("passwd");
		log.debug("userName:" + userName);
		log.debug("passwd:" + passwd);
		
		MessageVO message = new MessageVO(1, "Hello " + userName + "(" + passwd + ")");
		//VO ---> JSON
		String jsonString = new Gson().toJson(message);
		log.debug("jsonString : {}" + jsonString);
		
		return jsonString;

	}

}
