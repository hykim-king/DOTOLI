package com.pcwk.ehr.product_board.service;

import java.sql.SQLException;

import com.pcwk.ehr.product_board.domain.Product_BoardVO;

public interface Product_BoardService {
	
	/**
	 * 게시글 이미지 업데이트
	 * @param pbVO
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 */
	int update_Board_Images(Product_BoardVO pbVO) throws SQLException;
	
	/**
	 * 게시글 등록
	 * @param pbVO
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 */
	int doSave(Product_BoardVO pbVO) throws SQLException;
	
	

}
