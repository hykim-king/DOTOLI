package com.pcwk.ehr.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.email.dao.EmailDao;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml"
		                          , "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class UserDaoJUnit {

	final Logger log = LogManager.getLogger(getClass());

	UserVO userVO01;
	UserVO userVO02;
	UserVO userVO03;
	UserVO userVO04;
	
	SearchVO search;

	@Autowired // 테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	ApplicationContext context;

	@Autowired
	UserDao userdao;
	
	@Autowired
	EmailDao emailDao;
	

	@BeforeEach // @Test수행전 실행
	public void setUp() throws Exception {
		log.debug("context:{}", context);
		userVO01 = new UserVO("test01@gmail.com", "이영민01", "010934094211", "영민01", "인천 주안1동");
		userVO02 = new UserVO("test02@gmail.com", "이영민02", "01093409422", "영민02", "인천 주안2동");
		userVO03 = new UserVO("test03@gmail.com", "이영민03", "01093409423", "영민03", "인천 주안3동");
		userVO04 = new UserVO("zeromin1673@gmail.com", "이영민", "01093409421", "영민03", "인천 주안3동");

		search=new SearchVO();
	}

	//@Disabled
	@Test
	public void beans() {
		assertNotNull(context);
		assertNotNull(userdao);
		assertNotNull(emailDao);
	}
	
	@Test
	public void doSelectEamil_By_Name_Phone() throws NullPointerException, SQLException {
		
		String email = userdao.doSelectEmail_By_Name_Phone(userVO01);
		
		assertEquals("test01@gmail.com", email);
		
//		// NullPointerException이 발생 하면 성공
//		assertThrows(EmptyResultDataAccessException.class, () -> {
//			String email = userdao.doSelectEamil_By_Name_Phone(userVO01);
//		});
		
		
		
	}
	
	@Disabled
	@Test
	public void isExistsEmail() throws SQLException {
		
		userdao.deleteAll();
		emailDao.deleteAll();
		assertEquals(0, userdao.getCount());
		
		emailDao.doSave(userVO01.getEmail());
		userdao.doSave(userVO01);
		assertEquals(1, userdao.getCount());
		
		int cnt = userdao.isExistsEmail(userVO01.getEmail());
		
		assertEquals(1, cnt);
	}
	
	@Disabled
	@Test
	public void isExistsPhone() throws SQLException {
		
		userdao.deleteAll();
		emailDao.deleteAll();
		assertEquals(0, userdao.getCount());
		
		emailDao.doSave(userVO01.getEmail());
		userdao.doSave(userVO01);
		assertEquals(1, userdao.getCount());
		
		int cnt = userdao.isExistsPhone(userVO01.getAuth_code());
		
		assertEquals(1, cnt);
	}
	
	
	@Disabled
	@Test
	public void doDelete() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// 1. userVO01 데이터 저장
		// 2. userVO01 데이터 삭제
		// 3. 건수 0건
		
		//0.
		userdao.deleteAll();
		emailDao.deleteAll();
		assertEquals(0, userdao.getCount());
		
		//1.
		emailDao.doSave(userVO01.getEmail());
		userdao.doSave(userVO01);
		assertEquals(1, userdao.getCount());
		
		//2.
		int flag = userdao.doDelete(userVO01);
		assertEquals(1, flag);
		
		//3.
		assertEquals(0, userdao.getCount());
	}
	
	
	@Disabled
	@Test
	void doUpdate() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// 1. userVO01 데이터 저장
		// 2. userVO01수정 update
		// 3. update데이터 조회
		// 4. 비교
		
		// * 보완된 update: 2건 입력, 1건만 update
		
		
		// 0
		userdao.deleteAll();
		emailDao.deleteAll();
		
		// 1
		emailDao.doSave(userVO01.getEmail());
		int count = userdao.doSave(userVO01);
		assertEquals(1, count);
		
		emailDao.doSave(userVO02.getEmail());
		userdao.doSave(userVO02);
		
	
		userVO01.setNew_Email(userVO01.getEmail()+"_U");
		userVO01.setName(userVO01.getName()+"_U");
		userVO01.setPhone(userVO01.getPhone()+"1");
		userVO01.setNickname(userVO01.getNickname()+"_U");
		userVO01.setAddress(userVO01.getAddress()+"진흥아파트");
		
		// 2.
		
		emailDao.doUpdate_Email(userVO01);
		userVO01.changeEmail();
		count =userdao.doUpdate_Email(userVO01);
		assertEquals(1, count);
		
		// 3.
		UserVO outVO = userdao.doSelectOne(userVO01);
		//변경 하지 않은 UserVO
		UserVO noUpdateOutVO =userdao.doSelectOne(userVO02);
		
		// 4.
		isSameUser(userVO01, outVO);
		
		//변경 하지 않은 UserVO
		isSameUser(noUpdateOutVO, userVO02);
		
	}
	
	
	@Disabled
	@Test
	void doRetrieve() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// 1. saveAll()
		// 2. paging조회
		// 3. 조회 건수 10건

		// 0
		userdao.deleteAll();
		emailDao.deleteAll();
		
		// 1.
		emailDao.saveAll_email();
		userdao.saveAll();
		
		search.setPageNo(1);
		search.setPageSize(10);
		// 2.
		List<UserVO> list = userdao.doRetrieve(search);
		// 3.
		assertEquals(1, list.get(0).getUser_Id());

	}

	@Disabled
	@Test
	void getAll() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// -------------------------------------------
		// 1. 한 건 입력
		// 2. getAll()
		// 3. size()비교
		// -------------------------------------------

		// 위 부분 3회 반복
		// 입력 데이터 3건과 조회 데이터 비교

		// 0
		this.userdao.deleteAll();
		emailDao.deleteAll();

		// 1
		emailDao.doSave(userVO01.getEmail());
		userdao.doSave(userVO01);

		// 2
		List<UserVO> user1 = userdao.getAll();

		// 3
		assertEquals(user1.size(), 1);

		// 1
		emailDao.doSave(userVO02.getEmail());
		userdao.doSave(userVO02);

		// 2
		List<UserVO> user2 = userdao.getAll();

		// 3
		assertEquals(user2.size(), 2);

		// 1
		emailDao.doSave(userVO03.getEmail());
		userdao.doSave(userVO03);

		// 2
		List<UserVO> user3 = userdao.getAll();

		// 3
		assertEquals(user3.size(), 3);

		isSameUser(userVO03, user3.get(2));
		isSameUser(userVO02, user3.get(1));
		isSameUser(userVO01, user3.get(0));

	}

	// 메서드 예외사항 테스트:NullPointerException이 발생 하면 성공
	@Disabled
	@Test
	public void getFailure() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// 1. 건수 조회
		// 2. 한 건 등록
		// 3. 한건 조회

		// 0.
		userdao.deleteAll();
		emailDao.deleteAll();

		// 1.
		int count = userdao.getCount();
		assertEquals(0, count);

		// 2.
		emailDao.doSave(userVO01.getEmail());
		userdao.doSave(userVO01);

		// 3.
		String unKnownEmail = userVO01.getEmail() + "_99";

		userVO01.setEmail(unKnownEmail);

		// NullPointerException이 발생 하면 성공
		assertThrows(EmptyResultDataAccessException.class, () -> {
			UserVO outVO = userdao.doSelectOne(userVO01);
		});

	}

	@Disabled
	@Test
	public void getCount() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// 1. 건수 조회
		// 2. 한 건 등록
		// 3. 건수 조회:1

		// 4. 한 건 등록
		// 5. 건수 조회:2

		// 6. 한 건 등록
		// 7. 건수 조회:3

		// 0
		userdao.deleteAll();
		emailDao.deleteAll();

		// 1. 삭제 건수: 0건
		int count = userdao.getCount();
		assertEquals(0, count);

		// 2. 한 건 등록
		emailDao.doSave(userVO01.getEmail());
		userdao.doSave(userVO01);

		// 3. 건수 조회
		count = userdao.getCount();
		assertEquals(1, count);

		// 2. 한 건 등록
		emailDao.doSave(userVO02.getEmail());
		userdao.doSave(userVO02);

		// 3.건수 조회
		count = userdao.getCount();
		assertEquals(2, count);

		// 2. 한 건 등록
		emailDao.doSave(userVO03.getEmail());
		userdao.doSave(userVO03);

		// 3.건수 조회
		count = userdao.getCount();
		assertEquals(3, count);
	}

	@Disabled // 테스트 무시
	@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
	@Test
	public void addAndGet() throws SQLException {
		// 매번 동일 결과가 도출 되도록 작성.
		// 0. 전체 삭제
		// 1. 건수 조회
		// 2. 한 건 등록
		// 3. 건수 조회
		// 4. 한건 조회
		// 5. 등록데이터 입력데터 비교

		// 0
		userdao.deleteAll();
		emailDao.deleteAll();

		// 1. 삭제 건수: 0건
		int count = userdao.getCount();
		assertEquals(0, count);

		// 2. 단건등록
		emailDao.doSave(userVO01.getEmail());
		userdao.doSave(userVO01);

		// 3. 등록건수 조회
		count = userdao.getCount();
		assertEquals(1, count);

		// 4.
		UserVO outVO01 = userdao.doSelectOne(userVO01);
		// Not Null 확인
		assertNotNull(outVO01);

		// 5.
		isSameUser(outVO01, userVO01);

	}

	public void isSameUser(UserVO outVO01, UserVO userVO01) {
		assertEquals(outVO01.getEmail(), userVO01.getEmail());
		assertEquals(outVO01.getName(), userVO01.getName());
		assertEquals(outVO01.getPhone(), userVO01.getPhone());
		//----------------------------------------------------------------------
		assertEquals(outVO01.getNickname(), userVO01.getNickname());
		assertEquals(outVO01.getAddress(), userVO01.getAddress());
	}

	@AfterEach
	public void tearDown() throws Exception {
		log.debug("==========================================================");
		log.debug("=@After=");
		log.debug("==========================================================");
	}

}
