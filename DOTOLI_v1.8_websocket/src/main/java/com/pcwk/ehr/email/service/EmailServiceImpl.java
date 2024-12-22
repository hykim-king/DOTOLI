package com.pcwk.ehr.email.service;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.email.dao.EmailDao;
import com.pcwk.ehr.email.domain.EmailVO;

@Service
public class EmailServiceImpl implements EmailService {
	
final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	EmailDao emailDao;

	@Autowired
	private MailSender mailSender;

	@Override
	public int doSave(String email) throws SQLException {
		return emailDao.doSave(email);
	}

	@Override
	public int isExistsEmail(EmailVO inVO) throws SQLException, DataAccessException {
		return emailDao.isExistsEmail(inVO);
	}

	@Override
	public int doUpdate_Auth_Code(EmailVO inVO) throws SQLException {
		return emailDao.doUpdate_Auth_Code(inVO);
	}


	@Override
	public void send_Auth_Code(EmailVO inVO) throws Exception {
		
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			String auth_Code = EmailService.AuthCodeGenerator();
			
			log.debug("인증 번호 : {}", auth_Code);
			// 보내는 사람
			message.setFrom("karma9745@naver.com");

			// 받는 사람
			message.setTo(inVO.getEmail());

			// 제목
			message.setSubject("도토리 마켓 인증코드  : " + auth_Code);

			// 내용:
			message.setText("이용자님, 안녕하세요. \n" + "현재 본인 인증하기 중입니다. \n" + "귀하의 인증코드는  "  
	                         + auth_Code +  "  입니다.\n" + "도토리 마켓\n" +
			                "※본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다. ");

			mailSender.send(message);
			
			inVO.setAuth_code(auth_Code);
			
			emailDao.doUpdate_Auth_Code(inVO);
		} catch (Exception e) {
			log.debug("┌───────────────────────────────────────┐");
			log.debug("│ **Exception**                         │" + e.getMessage());
			log.debug("└───────────────────────────────────────┘");
		}

		log.debug("┌───────────────────────────────────────┐");
		log.debug("│ **mail send To**                      │" + inVO.getEmail());
		log.debug("└───────────────────────────────────────┘");
		
		
	}

	@Override
	public EmailVO doSelectOne(EmailVO inVO) throws SQLException, NullPointerException {
		return emailDao.doSelectOne(inVO);
	}

	@Override
	public int doUpdate_Is_Registered_1(String email) throws SQLException {
		return emailDao.doUpdate_Is_Registered_1(email);
	}

	@Override
	public int doUpdate_Is_Registered_0(String email) throws SQLException {
		return emailDao.doUpdate_Is_Registered_0(email);
	}
	


}
