package com.pcwk.ehr.user;

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
import com.pcwk.ehr.email.dao.EmailDao;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserVO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class UserControllerTest {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	UserDao userDao;
	
	@Autowired
	EmailDao emailDao;

	MockMvc mockMvc;

	UserVO userVO01;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		userVO01 = new UserVO("test01@gmail.com", "이영민01", "01093409421", "영민01", "인천 주안1동");

		log.debug(userVO01.toString());
	}

	@Disabled
	@Test
	void doSave() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ doSave()                                                │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		this.userDao.deleteAll();
		emailDao.deleteAll();

		// 1. url로 호출 , Method : GET/POST, Param:
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/doSave.do")
				.param("email", userVO01.getEmail()).param("name", userVO01.getName())
				.param("phone", userVO01.getPhone()).param("nickname", userVO01.getNickname())
				.param("address", userVO01.getAddress());

		// 2. 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 3. 호출 결과 받기
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();

		log.debug("returnBody : {}", returnBody);
		MessageVO resultMessage = new Gson().fromJson(returnBody, MessageVO.class);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals(userVO01.getName() + "님 등록 성공하였습니다.", resultMessage.getMessage());

	}

	@Disabled
	@Test
	public void doDelete() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ doDelete()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		// 1. 전체 삭제(UserDao)
		// 2. 단건 등록(UserDao)
		// 3. 한건 삭제(UserController 호출)

		// 1.
		this.userDao.deleteAll();
		emailDao.deleteAll();

		// 2.
		emailDao.doSave(userVO01.getEmail());
		int flag = userDao.doSave(userVO01);

		assertEquals(1, flag);

		// 3.1 url 호출, Method:get
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/doDelete.do").param("email",
				userVO01.getEmail());

		// 3.2 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 3. 호출 결과 받기
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();

		log.debug("returnBody : {}", returnBody);
		MessageVO resultMessage = new Gson().fromJson(returnBody, MessageVO.class);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals(userVO01.getEmail() + "님이 삭제 되었습니다.", resultMessage.getMessage());

	}

	@Disabled
	@Test
	public void doUpdate() throws Exception {

		// 1. 전체 삭제(UserDao)
		// 2. 단건 등록(UserDao)
		// 3. 단건 수정(UserController 호출)

		// 1.
		this.userDao.deleteAll();
		emailDao.deleteAll();

		// 2.
		
		emailDao.doSave(userVO01.getEmail());
		int flag = userDao.doSave(userVO01);

		assertEquals(1, flag);
		
		UserVO inVO = userDao.doSelectOne(userVO01);
		
		String upString = "_U";

		// 3.1
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/doUpdate.do")
				.param("email", inVO.getEmail())
				.param("name", inVO.getName()+upString)
				.param("phone", inVO.getPhone()+upString)
				.param("nickname", inVO.getNickname()+upString)
				.param("address", inVO.getAddress()+upString)
				.param("new_Email", inVO.getEmail()+upString);

		// 3.2
		// 2. 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 3. 호출 결과 받기
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();

		log.debug("returnBody : {}", returnBody);
		MessageVO resultMessage = new Gson().fromJson(returnBody, MessageVO.class);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals(inVO.getName()+ upString + "님 수정 성공하였습니다.", resultMessage.getMessage());

	}
	
	@Test
	public void test() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ehr/registerSave.do")
				.param("email", "zeromin1673@gmail.com")
				.param("name", "이영민")
				.param("phone", "01093409421")
				.param("nickname", "이영민")
				.param("address", "주안8동")
				.param("auth_code", "368975");

		// 3.2
		// 2. 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

		// 3. 호출 결과 받기
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		
		log.debug("returnBody : {}", returnBody);
		MessageVO resultMessage = new Gson().fromJson(returnBody, MessageVO.class);
		
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

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
