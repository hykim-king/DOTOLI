package com.pcwk.ehr.user.service;

import java.sql.SQLException;

import com.pcwk.ehr.user.domain.UserVO;

//2번째 user, 4번째 user가 등업 대상, 2번째 사용자를 등업 하고, 4번째 사용자 에서는  예외 발생
public class TestUserService extends UserServiceImpl {

	
	private String userId;

	public TestUserService(String userId) {
		super();
		this.userId = userId;
	}
	
	
//	protected void upgradeLevel(UserVO user) throws SQLException {  
//		//4번째 사용자가 들어 오면 예외 발생
//		if(userId.equals(user.getUserId())) {
//			throw new TestUserServiceException(userId+" 예외가 발생 했습니다.");
//		}
//		
//		super.upgradeLevel(user);
//		
//		
//	}
	
	
	
}
