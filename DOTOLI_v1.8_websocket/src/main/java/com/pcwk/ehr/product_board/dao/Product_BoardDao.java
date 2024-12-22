package com.pcwk.ehr.product_board.dao;

import java.sql.SQLException;

import com.pcwk.ehr.product_board.domain.Product_BoardVO;

public interface Product_BoardDao {
	
	
	int update_Board_Images(Product_BoardVO pbVO) throws SQLException;
	
	int doSave(Product_BoardVO pbVO) throws SQLException;
	
	

}
