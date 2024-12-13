package com.pcwk.ehr.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.user.domain.UserVO;

@Repository
public class UserDaoJdbc implements UserDao {
	final Logger log = LogManager.getLogger(UserDaoJdbc.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<UserVO> userMapper = new RowMapper<UserVO>() {

		@Override
		public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserVO outVO = new UserVO();
			// no
			outVO.setUser_Id(rs.getInt("USER_ID"));
			outVO.setEmail(rs.getString("EMAIL"));
			outVO.setName(rs.getString("NAME"));
			outVO.setPhone(rs.getString("PHONE"));
			outVO.setNickname(rs.getString("NICKNAME"));
			outVO.setAddress(rs.getString("ADDRESS"));
			outVO.setRegDt(rs.getString("REG_DT"));
			outVO.setRole(rs.getInt("ROLE"));

			log.debug("2.outVO: " + outVO.toString());
			return outVO;
		}

	};

	public UserDaoJdbc() {
		super();
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;

		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	/**
	 * 502건 데이터 입력, paging데이터 처리
	 * 
	 * @return
	 */
	@Override
	public int saveAll() {
		int cnt = 0;

		StringBuilder sb = new StringBuilder(300);
		sb.append("INSERT INTO member(email, name, phone, nickname, address)( \n");                                
		sb.append("SELECT 'james@naver.com' ||level email,                    \n");
		sb.append("       '이상무' ||level name,                               \n");
		sb.append("       '01093409421' || level phone,                       \n");      
		sb.append("       '닉네임' || level nickname,                          \n");
		sb.append("       '인천 주안' || level || '동' address                  \n");    
		sb.append("  FROM dual                                                \n");
		sb.append("  CONNECT BY LEVEL <= 502)                                 \n");

		cnt = this.jdbcTemplate.update(sb.toString());
		log.debug("총 등록 건수 :{}", cnt);

		return cnt;
	}

	@Override
	public List<UserVO> doRetrieve(DTO dto) {

		SearchVO inVO = (SearchVO) dto;

		List<UserVO> userList = new ArrayList<UserVO>();

		StringBuilder sb = new StringBuilder(1000);
		sb.append(" SELECT A.*, B.*                                          \n");   
		sb.append("   FROM (                                                 \n"); 
		sb.append(" 		SELECT tt1.rnum no,                              \n"); 
		sb.append(" 			   tt1.user_id,                              \n"); 
		sb.append(" 			   tt1.email,                                \n");  
		sb.append(" 			   tt1.name,                                 \n");
		sb.append(" 			   tt1.phone,                                \n"); 
		sb.append(" 			   tt1.nickname,                             \n");
		sb.append(" 			   tt1.address,                              \n");
		sb.append("                TO_CHAR(tt1.reg_dt,'YYYY/MM/DD') reg_dt,  \n");			   
		sb.append(" 			   tt1.role                                  \n");
		sb.append(" 		  FROM(                                          \n"); 
		sb.append(" 				SELECT rownum rnum, t1.*                 \n"); 
		sb.append(" 				  FROM (                                 \n"); 
		sb.append(" 							SELECT *                     \n"); 
		sb.append(" 							FROM member                  \n"); 
		sb.append("                                                          \n"); 
		sb.append(" 						--WHERE 조건                                                \n");  
		sb.append(" 							ORDER BY user_id ASC         \n"); 
		sb.append(" 				)t1                                      \n"); 
		sb.append(" 				WHERE ROWNUM <=( ? * (? - 1  )+? )       \n"); 
		sb.append(" 		)tt1                                             \n"); 
		sb.append(" 		WHERE rnum >=( ? * (? - 1  )+1 )                 \n"); 
		sb.append("   ) A                                                    \n"); 
		sb.append("   CROSS JOIN (                                           \n"); 
		sb.append(" 		SELECT COUNT(*) totalCnt                         \n"); 
		sb.append(" 		FROM member                                      \n"); 
		sb.append(" 	--WHERE 조건                                                                                              \n");  
		sb.append("   ) B                                                    \n");                           
		
		Object[] args = { inVO.getPageSize(), inVO.getPageNo(), inVO.getPageSize(), inVO.getPageSize(),
				inVO.getPageNo() };

		RowMapper<UserVO> users = new RowMapper<UserVO>() {

			@Override
			public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserVO outVO = new UserVO();
				// no
				outVO.setNo(rs.getInt("no"));
				outVO.setUser_Id(rs.getInt("user_id"));
				outVO.setEmail(rs.getString("email"));
				outVO.setName(rs.getString("name"));
				outVO.setPhone(rs.getString("phone"));
				outVO.setNickname(rs.getString("nickname"));
				outVO.setAddress(rs.getString("address"));
				outVO.setRegDt(rs.getString("reg_dt"));
				outVO.setRole(rs.getInt("role"));
				// totalCnt
				outVO.setTotalCnt(rs.getInt("totalCnt"));
				// --------------------------------------------------------------

				log.debug("outVO:{}", outVO);

				return outVO;
			}

		};

		userList = this.jdbcTemplate.query(sb.toString(), users, args);

		return userList;
	}

	@Override
	public List<UserVO> getAll() throws SQLException {
		List<UserVO> userList = new ArrayList<UserVO>();

		StringBuilder sb = new StringBuilder();
		sb.append("  SELECT                                    \n");
		sb.append("      user_id,                              \n");
		sb.append("      email,                                 \n");
		sb.append("      name,                                  \n");
		sb.append("      phone,                                  \n");
		sb.append("      nickname,                               \n");
		sb.append("      address,                                \n");
		sb.append("      TO_CHAR(reg_dt,'YYYY/MM/DD') reg_dt,   \n");
		sb.append("      role                                   \n");
		sb.append("  FROM member                               \n");
		sb.append("  ORDER BY user_id asc                     \n");

		userList = this.jdbcTemplate.query(sb.toString(), userMapper);

		return userList;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int getCount() throws SQLException {
		int count = 0;

		// 1.
		StringBuilder sb = new StringBuilder(50);
		sb.append(" SELECT COUNT(*) totalCnt \n");
		sb.append(" FROM  member             \n");

		count = this.jdbcTemplate.queryForObject(sb.toString(), Integer.class);
		log.debug("1. count:{}", count);

		return count;
	}

	@Override
	public void deleteAll() throws SQLException {
		// SQL작성만!
		// 1. Connection : X
		// 2. 자원 반납 : close() X
		StringBuilder sb = new StringBuilder(100);
		sb.append(" DELETE FROM member \n");
		// log.debug("2. sql:\n" + sb.toString());
		this.jdbcTemplate.update(sb.toString());

	}

	// 등록
	@Override
	public int doSave(UserVO inVO) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(200);
		sb.append("  INSERT INTO member ( \n");        
		sb.append("     email,            \n"); 
		sb.append("     name,             \n");
		sb.append("     phone,            \n"); 
		sb.append("     nickname,         \n"); 
		sb.append("     address           \n");    
		sb.append(" ) VALUES ( ?,         \n"); 
		sb.append("            ?,         \n"); 
		sb.append("            ?,         \n"); 
		sb.append("            ?,         \n"); 
		sb.append("            ?      )   \n");
		
		Object[] args = { inVO.getEmail(), inVO.getName(), inVO.getPhone()
				          , inVO.getNickname(), inVO.getAddress()};

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}

	// 단건조회
	/*
	 * alt+shift+a : 세로 편집
	 * sb.append(" SELECT                                                 \n");
	 * sb.append("     user_id,                                           \n");
	 * sb.append("     name,                                              \n");
	 * sb.append("     password,                                          \n");
	 * sb.append("     TO_CHAR(reg_dt,'YYYY/MM/DD HH24:MI:SS') reg_dt     \n");
	 * sb.append(" FROM                                                   \n");
	 * sb.append("     member                                             \n");
	 * sb.append(" WHERE  user_id = :user_id                              \n");
	 */
	@Override
	public UserVO doSelectOne(UserVO inVO) throws SQLException, EmptyResultDataAccessException, NullPointerException {

		UserVO outVO = null;

		// 1.
		StringBuilder sb = new StringBuilder(200);
		sb.append("SELECT user_id,                              \n");
		sb.append("       email,                                \n");
		sb.append("       name,                                 \n");
		sb.append("       phone,                                \n");
		sb.append("       nickname,                             \n");
		sb.append("       address,                              \n");
		sb.append("       TO_CHAR(reg_dt, 'YYYY/MM/DD') reg_dt, \n");
		sb.append("       role                                  \n");
		sb.append(" FROM member                                 \n");
		sb.append(" WHERE email = ?                             \n");

		Object[] args = { inVO.getEmail() };
		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		outVO = this.jdbcTemplate.queryForObject(sb.toString(), userMapper, args);

		// 조회 데이터가 없는 경우
		if (null == outVO) {
			throw new NullPointerException(inVO.getEmail() + "(이메일)를 확인 하세요.");
		}

		return outVO;
	}

	@Override
	public int doUpdate(UserVO inVO) throws SQLException {
		int flag = 0;
		StringBuilder sb = new StringBuilder(150);
		sb.append(" UPDATE member                   \n");
		sb.append("    SET   name  = ?          \n");
		sb.append(" 		,phone     = ?          \n");
		sb.append(" 		,nickname = ?          \n");
		sb.append(" 		,address     = ?          \n");
		sb.append(" WHERE                           \n");
		sb.append("         email = ?             \n");

		Object[] args = {  inVO.getName(), inVO.getPhone()
				          ,inVO.getNickname(), inVO.getAddress(), inVO.getEmail()};

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
		sb.append(" DELETE FROM member \n");
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
	public int isExistsEmail(String email) throws SQLException {
		
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(50);
		sb.append("SELECT COUNT(*) \n");
		sb.append("FROM member     \n");
		sb.append("WHERE email = ? \n");
		
		Object[] args = { email };
		
		flag = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);
		
		return flag;
	}

	@Override
	public int isExistsPhone(String phone) throws SQLException {
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(50);
		sb.append("SELECT COUNT(*) \n");
		sb.append("FROM member     \n");
		sb.append("WHERE phone = ? \n");
		
		Object[] args = { phone };
		
		flag = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);
		
		return flag;
	}

}
