package com.pcwk.ehr.product_board.service;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.product_board.dao.Product_BoardDao;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;

@Service
public class Product_BoardServiceImpl implements Product_BoardService {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	Product_BoardDao product_BoardDao;

	public Product_BoardServiceImpl() {
		super();
	}

	@Override
	public int update_Board_Images(Product_BoardVO pbVO) throws SQLException {
		return product_BoardDao.update_Board_Images(pbVO);
	}

	@Override
	public int doSave(Product_BoardVO pbVO) throws SQLException {
		
		return product_BoardDao.doSave(pbVO);
	}
	
	
	
	

}
