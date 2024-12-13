package com.pcwk.ehr.email.domain;

import com.pcwk.ehr.cmn.DTO;

public class EmailVO extends DTO {

	private String email; // 이메일
	private String auth_code; // 인증번호
	private int is_Registered; // 회원가입여부

	public EmailVO(String email) {
		super();
		this.email = email;
	}

	public EmailVO(String email, String auth_code, int is_Registered) {
		super();
		this.email = email;
		this.auth_code = auth_code;
		this.is_Registered = is_Registered;
	}

	public EmailVO() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public int getIs_Registered() {
		return is_Registered;
	}

	public void setIs_Registered(int is_Registered) {
		this.is_Registered = is_Registered;
	}

	@Override
	public String toString() {
		return "EmailVO [email=" + email + ", auth_code=" + auth_code + ", is_Registered=" + is_Registered + "]";
	}

	
	
}
