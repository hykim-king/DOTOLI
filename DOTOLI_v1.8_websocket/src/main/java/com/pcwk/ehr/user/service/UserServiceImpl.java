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
import org.springframework.dao.DataAccessException;
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
	public int doUpdate_Email(UserVO inVO) throws SQLException {
		return userDao.doUpdate_Email(inVO);
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

	@Override
	public String doSelectEmail_By_Name_Phone(UserVO inVO) throws SQLException, NullPointerException, DataAccessException {
		return userDao.doSelectEmail_By_Name_Phone(inVO);
	}

	@Override
	public int doUpdate_Other(UserVO inVO) throws SQLException {
		return userDao.doUpdate_Other(inVO);
	}

	@Override
	public int doUpdate_User_Image(UserVO inVO) throws SQLException {
		return userDao.doUpdate_User_Image(inVO);
	}


	

}
