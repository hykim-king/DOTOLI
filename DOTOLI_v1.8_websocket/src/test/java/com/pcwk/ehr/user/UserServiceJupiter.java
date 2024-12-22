/**
 * Package Name : com.pcwk.ehr.user <br/>
 * Class Name: UserServiceJupiter.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2024-11-28<br/>
 *
 * ------------------------------------------<br/>
 * @author :acorn
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

/**
 * @author acorn
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class UserServiceJupiter {
	final Logger log = LogManager.getLogger(getClass());

	@Autowired // 테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	ApplicationContext context;

	@Qualifier("userServiceImpl")
	@Autowired
	UserService userService;

	List<UserVO> users;

	@Autowired
	UserDao userDao;
	
	@Autowired
	EmailDao emailDao;

	@Autowired
	DataSource dataSource;


	// 스프링 DI(Dependency Injection)에서 특정한 이름을 가진 빈(bean)을 의존성 주입
	@Qualifier("dummyMailSender")
	@Autowired
	MailSender mailSender;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		users = Arrays.asList(new UserVO("test01@gmail.com", "이영민01", "01093409421", "영민01", "인천 주안1동")
		                    , new UserVO("test02@gmail.com", "이영민02", "01093409422", "영민02", "인천 주안2동")
		                    , new UserVO("test03@gmail.com", "이영민03", "01093409423", "영민03", "인천 주안3동")
				
		);

	}


	//@Disabled
	@Test
	public void doSave() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// 1. 등급 있는 사용자 등록, 등급 null인 사용자 등록
		// 2. 데이터 조회
		// 3. 비교(등급 null인 사용자 -> Level.BASIC)

		// 0
		userDao.deleteAll();
		emailDao.deleteAll();
		

		// 1
		UserVO user01 = users.get(2);

		UserVO user02 = users.get(0);

		int flag = emailDao.doSave(user01.getEmail());
		assertEquals(1, flag);
		
		flag = userService.doSave(user01);
		assertEquals(1, flag);

		emailDao.doSave(user02.getEmail());
		assertEquals(1, flag);
		
		flag = userService.doSave(user02);
		assertEquals(1, flag);

		// 2
		UserVO user01_1 = userDao.doSelectOne(user01);// BASIC
		UserVO user02_1 = userDao.doSelectOne(user02);// GOLD

		// log.debug(userWithoutLevelRead);

		assertEquals(user01.getEmail(), user01_1.getEmail());
		assertEquals(user02.getEmail(), user02_1.getEmail());

	}

	
	//@Disabled
	@Test
	void beans() {
		log.debug(context);
		log.debug(userDao);
		log.debug(userService);
		
		
		assertNotNull(context);
		assertNotNull(userDao);
		assertNotNull(userService);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

}
