package com.pcwk.ehr.email.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.user.dao.UserDaoJdbc;
import com.pcwk.ehr.user.domain.UserVO;

@Repository
public class EmailDaoJdbc implements EmailDao {

	final Logger log = LogManager.getLogger(UserDaoJdbc.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public EmailDaoJdbc() {
		super();
	}
	
	private RowMapper<EmailVO> emailMapper = new RowMapper<EmailVO>() {

		@Override
		public EmailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmailVO outVO = new EmailVO();
			// no
			outVO.setEmail(rs.getString("email"));
			outVO.setAuth_code(rs.getString("auth_code"));
			outVO.setIs_Registered(rs.getInt("is_registered"));

			log.debug("2.outVO: " + outVO.toString());
			return outVO;
		}

	};

	@Override
	public int saveAll_email() {

		int cnt = 0;

		StringBuilder sb = new StringBuilder(300);

		sb.append("INSERT INTO email_auth(email)(          \n");
		sb.append("SELECT 'james@naver.com' || level email \n");
		sb.append("  FROM dual                             \n");
		sb.append("  CONNECT BY LEVEL <= 502)             \n");

		cnt = this.jdbcTemplate.update(sb.toString());
		log.debug("총 등록 건수 :{}", cnt);

		return cnt;
	}

	@Override
	public void deleteAll() throws SQLException {
		StringBuilder sb = new StringBuilder(100);
		sb.append(" DELETE FROM email_auth \n");

		int cnt = this.jdbcTemplate.update(sb.toString());
		log.debug("총 삭제 건수 :{}", cnt);

	}

	@Override
	public int getCount() throws SQLException {
		int count = 0;

		// 1.
		StringBuilder sb = new StringBuilder(50);
		sb.append(" SELECT COUNT(*) totalCnt \n");
		sb.append(" FROM  email_auth         \n");

		count = this.jdbcTemplate.queryForObject(sb.toString(), Integer.class);
		log.debug("1. count:{}", count);

		return count;
	}

	@Override
	public int doSave(String email) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(200);
		sb.append("INSERT INTO email_auth (email) VALUES (?) ");

		Object[] args = { email };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}

	@Override
	public int doUpdate_Email(UserVO inVO) throws SQLException {
		int flag = 0;
		StringBuilder sb = new StringBuilder(150);
		sb.append(" UPDATE email_auth         \n");
		sb.append("    SET email = ?          \n");
		sb.append(" WHERE email = ?           \n");

		Object[] args = { inVO.getNew_Email(), inVO.getEmail()};

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}

	@Override
	public int doDelete(UserVO inVO) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(50);
		sb.append(" DELETE FROM email_auth \n");
		sb.append(" WHERE email = ?  \n");

		Object[] args = { inVO.getEmail() };
		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		log.debug("2.flag:{}", flag);

		return flag;
	}

	@Override
	public int doUpdate_Auth_Code(EmailVO inVO) throws SQLException {
		int flag = 0;
		StringBuilder sb = new StringBuilder(150);
		sb.append(" UPDATE email_auth         \n");
		sb.append("    SET auth_code = ?          \n");
		sb.append(" WHERE email = ?           \n");

		Object[] args = { inVO.getAuth_code(), inVO.getEmail()};

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}

	@Override
	public int isExistsEmail(EmailVO inVO) throws SQLException, DataAccessException{
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(50);
		sb.append("SELECT COUNT(*) \n");
		sb.append("FROM email_auth \n");
		sb.append("WHERE email = ? \n");
		
		Object[] args = { inVO.getEmail() };
		
		flag = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);
		
		return flag;
	}

	@Override
	public EmailVO doSelectOne(EmailVO inVO) throws SQLException, NullPointerException {
		EmailVO outVO = null;

		// 1.
		StringBuilder sb = new StringBuilder(200);
		sb.append("SELECT email,                            \n");
		sb.append("       auth_code,                        \n");
		sb.append("       is_registered                     \n");
		sb.append(" FROM email_auth                         \n");
		sb.append(" WHERE email = ?                         \n");

		Object[] args = { inVO.getEmail() };
		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		outVO = this.jdbcTemplate.queryForObject(sb.toString(), emailMapper, args);

		// 조회 데이터가 없는 경우
		if (null == outVO) {
			throw new NullPointerException(inVO.getEmail() + "(이메일)를 확인 하세요.");
		}

		return outVO;
	}

	@Override
	public int doUpdate_Is_Registered_1(String email) throws SQLException {
		int flag = 0;
		StringBuilder sb = new StringBuilder(100);
		sb.append("UPDATE email_auth        \n");
		sb.append("   SET is_registered = 1 \n");
		sb.append(" WHERE email = ?         \n");

		Object[] args = { email };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}

	@Override
	public int doUpdate_Is_Registered_0(String email) throws SQLException {
		int flag = 0;
		StringBuilder sb = new StringBuilder(100);
		sb.append("UPDATE email_auth        \n");
		sb.append("   SET is_registered = 0 \n");
		sb.append(" WHERE email = ?         \n");

		Object[] args = { email };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}
}
