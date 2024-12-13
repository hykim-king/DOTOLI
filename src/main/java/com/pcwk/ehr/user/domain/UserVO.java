package com.pcwk.ehr.user.domain;

import com.pcwk.ehr.cmn.DTO;

public class UserVO extends DTO {

	private int user_Id;// 유저id
	private String email;// 이메일
	private String name;// 이름
	private String phone;// 전화번호
	private String nickname; // 닉네임
	private String address; // 주소
	private String regDt; // 가입일
	private int role; // 역할
	private String new_Email; // 새 이메일
	private String auth_code; // 입력받은 이메일

	public UserVO() {
		super();
	}

	public UserVO(String email, String name, String phone, String nickname, String address) {
		super();
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.nickname = nickname;
		this.address = address;
	}

	public int getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getNew_Email() {
		return new_Email;
	}

	public void setNew_Email(String new_Email) {
		this.new_Email = new_Email;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public void changeEmail() {
		this.email = this.new_Email;
	}

	@Override
	public String toString() {
		return "UserVO [user_Id=" + user_Id + ", email=" + email + ", name=" + name + ", phone=" + phone + ", nickname="
				+ nickname + ", address=" + address + ", regDt=" + regDt + ", role=" + role + "]";
	}

}