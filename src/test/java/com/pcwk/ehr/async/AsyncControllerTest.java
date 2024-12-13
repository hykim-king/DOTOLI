package com.pcwk.ehr.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class AsyncControllerTest {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void asyncResult() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ asyncResult()                                           │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		// 1. url로 호출 , Method : GET/POST, Param:
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/async/async_result.do")
				.param("username","이상무01")
				.param("passwd", "4321");

		// 2. 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 3. ModelAndView
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		
		log.debug("returnBody : {}",returnBody);
		
		MessageVO resultMassage = new Gson().fromJson(returnBody, MessageVO.class);


		assertEquals("Hello 이상무01(4321)", resultMassage.getMessage());
		assertEquals(1, resultMassage.getMessageId());

	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Disabled
	@Test
	void beans() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ beans()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		log.debug("webApplicationContext : {}", webApplicationContext);
		assertNotNull("mockMvc : {}", mockMvc);

		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
	}

}
