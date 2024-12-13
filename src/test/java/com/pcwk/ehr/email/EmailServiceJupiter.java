package com.pcwk.ehr.email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.email.dao.EmailDao;
import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.email.service.EmailService;
import com.pcwk.ehr.user.dao.UserDao;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EmailServiceJupiter {
	
	final Logger log = LogManager.getLogger(getClass());

	@Autowired // 테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	ApplicationContext context;

	@Qualifier("emailServiceImpl")
	@Autowired
	EmailService emailService;

	@Autowired
	UserDao userDao;
	
	@Autowired
	EmailDao emailDao;

	@Autowired
	DataSource dataSource;
	
	EmailVO emailVO01;
	EmailVO emailVO02;
	EmailVO emailVO03;


	// 스프링 DI(Dependency Injection)에서 특정한 이름을 가진 빈(bean)을 의존성 주입
	@Autowired
	MailSender mailSender;

	
	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		emailVO01 = new EmailVO("zeromin1673@gmail.com", "123312", 0);
		emailVO02 = new EmailVO("testEmail02@gmail.com", "444134", 0);
		emailVO03 = new EmailVO("testEmail03@gmail.com", "551232", 0);
	}
	
	@Test
	void test() {
		assertNotNull(context);
		assertNotNull(userDao);
		assertNotNull(emailDao);
		assertNotNull(emailService);
		assertNotNull(mailSender);
	}
	
	@Test
	void isExistsEmail() throws SQLException {
		
		emailDao.deleteAll();
		
		emailDao.doSave(emailVO02.getEmail());
		
		assertEquals(1, emailDao.getCount());
		
		int flag = emailService.isExistsEmail(emailVO02);
		
		assertEquals(1, flag);
		
	}
	
	@Test
	void send_Auth_Code() throws Exception {
		
		emailDao.deleteAll();
		
		emailDao.doSave(emailVO01.getEmail());
		
		assertEquals(1, emailDao.getCount());
		
		emailService.send_Auth_Code(emailVO01);		
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}


}
