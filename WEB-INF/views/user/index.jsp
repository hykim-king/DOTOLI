<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>HTML 기본 문서</title>
<link rel="stylesheet" href="/ehr/resources/assets/css/index.css" />
</head>
<body>
	<div>
		<h1>번개장터로 중고거래 시작하기</h1>
	</div>
	<div>
		<h3>간편하게 가입하고 상품을 확인하세요</h3>
	</div>

		<div>
			<button class="button naver" onclick="location.href='<c:url value="/user/login.do" />'">로그인</button>
		</div>
		<div>
			<button class="button own"
				onclick="location.href='<c:url value="/user/register.do" />'">회원가입</button>
		</div>

		<br />
		<hr />
		<footer>
			<p>
				도움이 필요하면 <a href="#">이메일</a> 또는 고객센터 1670-2910로 문의 부탁드립니다.
			</p>
			<p>고객센터 운영시간: 09:18시(점심시간 12~13시, 주말/공휴일 제외)</p>
		</footer>
</body>
</html>
