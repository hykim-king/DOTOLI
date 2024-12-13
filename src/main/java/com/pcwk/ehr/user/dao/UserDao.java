package com.pcwk.ehr.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

public interface UserDao {


	int doDelete(UserVO inVO) throws SQLException;
	
	int doUpdate(UserVO inVO) throws SQLException;
	
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

	// 단건조회
	UserVO doSelectOne(UserVO inVO) throws SQLException, NullPointerException;

}