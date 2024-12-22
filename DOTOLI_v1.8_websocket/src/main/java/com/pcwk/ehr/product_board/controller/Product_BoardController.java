package com.pcwk.ehr.product_board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;
import com.pcwk.ehr.product_board.service.Product_BoardService;
import com.pcwk.ehr.user.domain.UserVO;

@Controller
public class Product_BoardController {

	final Logger log = LogManager.getLogger(getClass());

	@Qualifier("product_BoardServiceImpl")
	@Autowired
	private Product_BoardService product_BoardService;

	public Product_BoardController() {
		super();
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│    **product_BoardService() 생성**     │");
		log.debug("└───────────────────────────────────────┘");
	}
	
	// 게시글 등록
	@RequestMapping(value = "/product_board/post.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String post_Product_Board(HttpSession session, @ModelAttribute Product_BoardVO pbVO, @RequestParam("board_Images") MultipartFile[] board_Images) {

		log.debug("post_Product_Board 호출");

		String message = "";
		int flag = 0;

		UserVO user = (UserVO) session.getAttribute("user");

		try {

			List<String> fileNames = new ArrayList<String>();
			// 1. 파일 저장 경로 설정
			String uploadDir = "C:/resources/board_image/"; // 유저 이미지 파일 저장 경로

			for (MultipartFile file : board_Images) {

				// 고유 파일명_원본파일명 으로 저장(중복 방지)
				String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

				File dest = new File(uploadDir + fileName); // 실제 저장 경로 지정

				// 2. 파일 저장
				file.transferTo(dest);

				fileNames.add(fileName);

			}

			log.debug("fileNames 리스트 : {}", fileNames.toString());

			// 3. DB에 업데이트
			pbVO.setSeller_Id(user.getUser_Id());
			pbVO.setBoard_Image(fileNames);
			
			log.debug("pbVO : {}", pbVO.toString());
			flag = product_BoardService.doSave(pbVO); // DB 업데이트 호출

			if (flag == 1) {
				message = "게시글 등록 성공";
			} else {
				message = "DB에 게시글 등록 중 오류가 발생하였습니다.";
			}

		} catch (IOException e) {
			return new Gson().toJson(new MessageVO(0, "서버에 파일 저장 중 오류가 발생하였습니다."));

		} catch (Exception e) {
			log.debug("예외 발생");
			return new Gson().toJson(new MessageVO(0, "게시글 등록 중 오류 발생"));
		}

		log.debug("예외 발생 안했는데?");
		return new Gson().toJson(new MessageVO(flag, message));

	}

	// 이미지 업데이트
	@RequestMapping(value = "/product_board/uploadBoard_Images.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String uploadBoard_Images(HttpSession session, @RequestParam("board_Images") MultipartFile[] board_Images) {

		log.debug("uploadBoard_Images 호출");

		String message = "";
		int flag = 0;

		UserVO user = (UserVO) session.getAttribute("user");

		Product_BoardVO pbVO = new Product_BoardVO();

		try {

			List<String> fileNames = new ArrayList<String>();
			// 1. 파일 저장 경로 설정
			String uploadDir = "C:/resources/board_image/"; // 유저 이미지 파일 저장 경로

			for (MultipartFile file : board_Images) {

				// 고유 파일명_원본파일명 으로 저장(중복 방지)
				String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

				File dest = new File(uploadDir + fileName); // 실제 저장 경로 지정

				// 2. 파일 저장
				file.transferTo(dest);

				fileNames.add(fileName);

			}

			log.debug("fileNames 리스트 : {}", fileNames.toString());

			// 3. DB에 업데이트
			pbVO.setBoard_Id(1);
			pbVO.setBoard_Image(fileNames);
			flag = product_BoardService.update_Board_Images(pbVO); // DB 업데이트 호출

			if (flag == 1) {
				message = "이미지 업데이트 성공";
			} else {
				message = "DB에 파일 이미지 업데이트 중 오류가 발생하였습니다.";
			}

		} catch (IOException e) {
			return new Gson().toJson(new MessageVO(0, "서버에 파일 저장 중 오류가 발생하였습니다."));

		} catch (Exception e) {
			log.debug("예외 발생");
			return new Gson().toJson(new MessageVO(0, "이미지 업데이트 중 오류 발생"));
		}

		log.debug("예외 발생 안했는데?");
		return new Gson().toJson(new MessageVO(flag, message));

	}

}
