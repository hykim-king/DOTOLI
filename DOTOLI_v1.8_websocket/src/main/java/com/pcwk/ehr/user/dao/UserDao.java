package com.pcwk.ehr.user.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

public interface UserDao {


	int doDelete(UserVO inVO) throws SQLException;
	
	/**
	 * 이메일 업데이트
	 * @param inVO
	 * @return 1(성공), 0(실패)
	 * @throws SQLException
	 */
	int doUpdate_Email(UserVO inVO) throws SQLException;
	
	/**
	 * 유저 이미지 업데이트
	 * @param inVO
	 * @return 1(성공), 0(실패)
	 * @throws SQLException
	 */
	int doUpdate_User_Image(UserVO inVO) throws SQLException;
	
	/**
	 * 기타 회원 정보 업데이트
	 * @param inVO
	 * @return 1(성공), 0(실패)
	 * @throws SQLException
	 */
	int doUpdate_Other(UserVO inVO) throws SQLException;
	
	int saveAll();

	List<UserVO> doRetrieve(DTO dto);

	List<UserVO> getAll() throws SQLException;


	int getCount() throws SQLException;
	
	/**
	 * 이메일 중복체크
	 * @param email
	 * @return 0(중복 x)/1(중복 o)
	 * @throws SQLException
	 */
	int isExistsEmail(String email) throws SQLException;
	
	/**
	 * 전화번호 중복체크
	 * @param phone
	 * @return 0(중복 x)/1(중복 o)
	 * @throws SQLException
	 */
	int isExistsPhone(String phone) throws SQLException;

	void deleteAll() throws SQLException;

	// 등록
	int doSave(UserVO inVO) throws SQLException;

	/**
	 * 이메일로 회원 단건 조회
	 * @param inVO
	 * @return
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	UserVO doSelectOne(UserVO inVO) throws SQLException, NullPointerException;
	
	/**
	 * name 과 phone 으로 email 조회
	 * @param inVO
	 * @return email(String)
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	String doSelectEmail_By_Name_Phone(UserVO inVO) throws SQLException, NullPointerException, DataAccessException;
	

}