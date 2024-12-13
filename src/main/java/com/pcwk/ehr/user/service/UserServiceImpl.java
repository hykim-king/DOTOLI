/**
 * Package Name : com.pcwk.ehr.user.service <br/>
 * Class Name: UserService.java              <br/>
 * Description:                              <br/>
 * Modification imformation :                <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2024-11-28                      <br/>
 *
 * ------------------------------------------<br/>
 * @author :acorn
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserVO;

/**
 * @author acorn
 *
 */
@Service
public class UserServiceImpl implements UserService {
	final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	UserDao userDao;

	@Autowired
	private MailSender mailSender;

	public UserServiceImpl() {

	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 회원삭제
	 * 
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	@Override
	public int doDelete(UserVO inVO) throws SQLException {
		return userDao.doDelete(inVO);
	}

	/**
	 * 회원 수정
	 * 
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	@Override
	public int doUpdate(UserVO inVO) throws SQLException {
		return userDao.doUpdate(inVO);
	}

	/**
	 * 회원 목록 페이징 처리
	 * 
	 * @param dto
	 * @return List<UserVO>
	 */
	@Override
	public List<UserVO> doRetrieve(DTO dto) {
		return userDao.doRetrieve(dto);
	}

	/**
	 * 회원 상세 조회
	 * 
	 * @param inVO
	 * @return UserVO
	 * @throws SQLException
	 * @throws EmptyResultDataAccessException
	 * @throws NullPointerException
	 */
	@Override
	public UserVO doSelectOne(UserVO inVO) throws SQLException, EmptyResultDataAccessException, NullPointerException {
		return userDao.doSelectOne(inVO);
	}

	/**
	 * 회원등록(가입)
	 * 
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	@Override
	public int doSave(UserVO inVO) throws SQLException {
		return userDao.doSave(inVO);
	}

	@Override
	public int isExistsEmail(String email) throws SQLException {
		return userDao.isExistsEmail(email);
	}

	@Override
	public int isExistsPhone(String phone) throws SQLException {
		return userDao.isExistsPhone(phone);
	}

	
//	/**
//	 * 등업 대상자에게 email 발송!
//	 * 
//	 * @param user
//	 */
//	private void sendUpgradeEmail(UserVO user) {
//		// 보내는 사람
//		// 받는 사람
//		// 제목
//		// 내용
//
//		try {
//			SimpleMailMessage message = new SimpleMailMessage();
//			// 보내는 사람
//			message.setFrom("jamesol@naver.com");
//
//			// 받는 사람
//			message.setTo(user.getEmail());
//
//			// 제목
//			message.setSubject("등업 안내!");
//
//			// 내용:
//			// {이상무}님의 등급이 {GOLD로} 등업 되었습니다.
//
//			message.setText(user.getName() + "님의 등급이 " + user.getGrade() + " 등업 되었습니다.");
//
//			mailSender.send(message);
//		} catch (Exception e) {
//			log.debug("┌───────────────────────────────────────┐");
//			log.debug("│ **Exception**                         │" + e.getMessage());
//			log.debug("└───────────────────────────────────────┘");
//		}
//
//		log.debug("┌───────────────────────────────────────┐");
//		log.debug("│ **mail send To**                      │" + user.getEmail());
//		log.debug("└───────────────────────────────────────┘");
//	}

	

}
