package com.pcwk.ehr.email.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.user.domain.UserVO;

public interface EmailDao {

	int saveAll_email();

	void deleteAll() throws SQLException;

	int getCount() throws SQLException;

	// 등록
	int doSave(String email) throws SQLException;
	
	int doUpdate_Email(UserVO inVO) throws SQLException;
	
	int doUpdate_Auth_Code(EmailVO inVO) throws SQLException;
	
	int doUpdate_Is_Registered_1(String email) throws SQLException;
	
	int doUpdate_Is_Registered_0(String email) throws SQLException;
	
	int isExistsEmail(EmailVO inVO) throws SQLException, DataAccessException;
	
	int doDelete(UserVO inVO) throws SQLException;
	
	EmailVO doSelectOne(EmailVO inVO) throws SQLException, NullPointerException;

}
