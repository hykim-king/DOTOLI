<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>HTML 기본 문서</title>
<link rel="stylesheet" href="/ehr/resources/assets/css/login.css" />
<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
</head>

<body>
	<div id="login-wrap">
		<h1 class="logo">
			<a href="#"><img src="/ehr/resources/assets/images/logo.svg" alt="도토리"></a>
		</h1>

		<form id="registerForm" method="POST" action="">

			<!-- 이메일 입력 -->
			<div class="form">
				<input type="email" id="email" name="email"
					placeholder="아이디(이메일)을 입력하세요." minlength="3" maxlength="320"
					required> <span id="emailMessage"></span>
			</div>

			<!-- 인증 번호 요청 -->
			<div class="form">
				<div class="auth-input-wrap">
					<input type="text" id="auth-code" name="auth_code"
						placeholder="인증코드를 입력하세요." minlength="6" maxlength="6" disabled>
					<span id="sendAuthBtn" class="text-button">발송</span>
				</div>
				<span id="authMessage"></span>
			</div>

			<div class="btm">
				<a href="#" class="idpw_search">아이디(이메일) 찾기</a>
			</div>

			<!-- 로그인 제출 -->
			<div>
				<button id="login" class="button own" type="submit">로그인</button>
				<span id="loginMessage"></span>
			</div>

		</form>

		<div>
			<button id="register_btn" class="button own"
				onclick="location.href='<c:url value="/user/login/register.do"/>'">
				회원가입</button>
		</div>


		<footer class="footer">
			<p>
			<hr>
			<br>&copy; Dotoli Corp. All rights reserved.
			</p>
			<p>
				도움이 필요하면 <a href="#">이메일</a> 또는 고객센터 1670-2910로 문의 부탁드립니다.
			</p>
			<p>고객센터 운영시간: 09:18시(점심시간 12~13시, 주말/공휴일 제외)</p>
			<br>
		</footer>

		<div></div>


		<script>
			// 인증 번호 요청
			$('#sendAuthBtn').on('click', function() {

				const email = $('#email').val();

				// http 요청
				$.ajax({
					url : '/ehr/email/send_Login.do',
					method : 'POST',
					contentType : 'application/json',
					data : JSON.stringify({
						email : email
					}),
					success : function(response) {
						if (response.messageId === 1) {
							$('#emailMessage').text('인증 번호가 발송되었습니다.');
							$('#auth-code').prop('disabled', false);

						} else {
							alert(response.message);
						}
					},
					error : function() {
						$('#authMessage').text('인증 번호 요청 중 에러가 발생했습니다.');
					}
				}); // http 요청 -- end
			}); // 인증번호 요청 -- end

			// 로그인
			$('#login').on('click', function(event) {

				event.preventDefault();

				const formData = {
					email : $('#email').val(),
					auth_code : $('#auth-code').val(),
				};

				// http 요청
				$.ajax({
					url : '/ehr/doLogin.do',
					method : 'POST',
					contentType : 'application/json',
					data : JSON.stringify(formData),
					success : function(response) {
						if (response.messageId === 1) {
							alert(response.message);
							window.location.href = "/ehr/main.do"
						} else {
							$('#loginMessage').text(response.message);
						}
					},
					error : function() {
						$('#loginMessage').text(response.message);
					}
				}); // http 요청 -- end
			}); // 회원가입 -- end
		</script>
</body>

</html>