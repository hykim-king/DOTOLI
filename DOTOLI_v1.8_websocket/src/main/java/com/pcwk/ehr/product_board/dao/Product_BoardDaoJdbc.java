package com.pcwk.ehr.product_board.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;
import com.pcwk.ehr.user.dao.UserDaoJdbc;

@Repository
public class Product_BoardDaoJdbc implements Product_BoardDao {

	final Logger log = LogManager.getLogger(UserDaoJdbc.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Product_BoardDaoJdbc() {
		super();
	}

	@Override
	public int doSave(Product_BoardVO pbVO) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(150);

		String board_ImagesJson = new Gson().toJson(pbVO.getBoard_Image()); // 파일명 목록을 JSON 형식으로 변환

		sb.append("INSERT INTO product_board ( \n");
		sb.append("            seller_id,      \n");
		sb.append("			   board_title,    \n");
		sb.append("			   board_image,    \n");
		sb.append("			   category_id,    \n");
		sb.append("			   price,          \n");
		sb.append("			   board_content,  \n");
		sb.append("			   how_trade,      \n");
		sb.append("			   trade_address ) \n");
		sb.append("   VALUES ( ?,              \n");
		sb.append("            ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("		       ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("			   ? )               ");

		Object[] args = { pbVO.getSeller_Id(), pbVO.getBoard_Title(), board_ImagesJson
				         , pbVO.getCategory_Id(), pbVO.getPrice(), pbVO.getBoard_Content()
				         , pbVO.getHow_Trade(), pbVO.getTrade_Address()};

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
	public int update_Board_Images(Product_BoardVO pbVO) throws SQLException {

		int flag;

		StringBuilder sb = new StringBuilder(100);

		String board_ImagesJson = new Gson().toJson(pbVO.getBoard_Image()); // 파일명 목록을 JSON 형식으로 변환

		sb.append("UPDATE product_board   \n");
		sb.append("   SET board_image = ? \n");
		sb.append(" WHERE board_id = ?      ");

		Object[] args = { board_ImagesJson, pbVO.getBoard_Id() };

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
