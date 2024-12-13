package com.pcwk.ehr.user.service;

public class TestUserServiceException extends RuntimeException {

	public TestUserServiceException() {}
	
	public TestUserServiceException(String message) {
		super(message);
		System.out.println("********************************");
		System.out.println("*TestUserServiceException()    *"+message);
		System.out.println("********************************");
	}
	
}
