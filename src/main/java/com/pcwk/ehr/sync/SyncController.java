package com.pcwk.ehr.sync;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SyncController {

	// sync/sync_index.do
	@RequestMapping(value = "/sync/sync_index.do", method = RequestMethod.GET)
	public String handlerSyncIndex() {
		System.out.println("handleqrSyncIndex");

		return "sync/sync_index";
	}

	@RequestMapping(value = "/sync/sync_result.do", method = RequestMethod.POST)
	public String syncResult(HttpServletRequest req, Model model) throws UnsupportedEncodingException {

		// 요청 인코딩
		req.setCharacterEncoding("UTF-8");
		System.out.println("syncResult");

		String name = req.getParameter("name");
		System.out.println("name : " + name);

		model.addAttribute("req_name", name); // 서버 view로 데이터 전송

		return "sync/sync_index";
	}

}
