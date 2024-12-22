package com.pcwk.ehr.email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.email.dao.EmailDao;
import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EmailDaoJunit {

	final Logger log = LogManager.getLogger(getClass());

	UserVO userVO01;
	UserVO userVO02;
	UserVO userVO03;
	
	EmailVO emailVO01;
	EmailVO emailVO02;
	EmailVO emailVO03;

	SearchVO search;

	@Autowired // 테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	ApplicationContext context;

	@Autowired
	UserDao userDao;

	@Autowired
	EmailDao emailDao;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("context:{}", context);
		userVO01 = new UserVO("test01@gmail.com", "이영민01", "01093409421", "영민01", "인천 주안1동");
		userVO02 = new UserVO("test02@gmail.com", "이영민02", "01093409422", "영민02", "인천 주안2동");
		userVO03 = new UserVO("test03@gmail.com", "이영민03", "01093409423", "영민03", "인천 주안3동");
		
		emailVO01 = new EmailVO("testEmail01@gmail.com", "123312", 0);
		emailVO02 = new EmailVO("testEmail02@gmail.com", "444134", 0);
		emailVO03 = new EmailVO("testEmail03@gmail.com", "551232", 0);
		

		search=new SearchVO();
	}
	
	//@Disabled
	@Test
	public void beans() {
		assertNotNull(context);
		assertNotNull(userDao);
		assertNotNull(emailDao);
	}
	
	@Test
	public void saveAll_email() throws SQLException {
		
		userDao.deleteAll();
		emailDao.deleteAll();
		
		emailDao.saveAll_email();
		
		int cnt = emailDao.getCount();
		
		assertEquals(502, cnt);
		
		cnt = emailDao.doSave(userVO01.getEmail());
		
		assertEquals(1, cnt);
		
	}
	
	@Test
	public void doDelete() throws SQLException {
		
		userDao.deleteAll();
		emailDao.deleteAll();
		
		emailDao.doSave(userVO01.getEmail());
		
		assertEquals(1, emailDao.getCount());

		emailDao.doDelete(userVO01);
		
		assertEquals(0, emailDao.getCount());		
		
	}
	
	@Test
	public void doUpdate_Auth_Code() throws SQLException, NullPointerException {
		
		userDao.deleteAll();
		emailDao.deleteAll();
		
		emailDao.doSave(emailVO01.getEmail());
		assertEquals(1, emailDao.getCount());
		
		emailVO01.setAuth_code("010101");
		
		emailDao.doUpdate_Auth_Code(emailVO01);
		
		EmailVO outVO = emailDao.doSelectOne(emailVO01);
		
		assertEquals("010101", outVO.getAuth_code());
		
	}
	
	@Test
	public void doUpdate_Is_Registered() throws SQLException, NullPointerException {
		
		userDao.deleteAll();
		emailDao.deleteAll();
		
		emailDao.doSave(emailVO01.getEmail());
		assertEquals(1, emailDao.getCount());
		
		emailDao.doUpdate_Is_Registered_1(emailVO01.getEmail());

		EmailVO outVO = emailDao.doSelectOne(emailVO01);
		
		
		assertEquals(1, outVO.getIs_Registered());
		assertNotNull(outVO);
		
	}
	

	@AfterEach
	void tearDown() throws Exception {
	}

}
