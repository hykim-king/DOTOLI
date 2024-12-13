<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>async_제목</title>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script> -->
<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
<script>
	//DOM문서(HTML) 문서가 로드가 완료되면 함수 수행!!!!!!!!
	document.addEventListener("DOMContentLoaded", function() {
		console.log("DOMContentLoaded~~ ^_^");
		
		let username = document.querySelector("#username");
		let passwd = document.querySelector("#passwd");
		
		console.log("username : " + username);
		console.log("passwd : " + passwd);
	
		$("#sendBtn").on("click", function() {
			alert("Handler for 'click' called. ");
			asyncSend();
		});
	
		function asyncSend() {
			console.log("username : " + username.value);
			console.log("passwd : " + passwd.value);
			
			$.ajax({
				type : "POST",
				url : "/ehr/async/async_result.do",
				async : "true",
				dataType : "html",
				data : {
					"username" : username.value,
					"passwd" : passwd.value
				},
				success : function(response) {
					console.log("success:" + response);
					result.innerHTML = response;
				},
				error : function(response) {
					console.log("error:" + response);
				}
			});
		} // -- asyncSend()
	
	}); // -- DOMContentLoaded
</script>


</head>
<body>
	<h2>async_index</h2>

	<form id="asyncForm">
		<label for="username">아이디</label> 
		<input type="text" name="username" id="username"> 
		<label for="passwd">비밀번호</label> 
		<input type="password" name="passwd" id="passwd"> 
		<input type="button" id="sendBtn" value="전송">
	</form>
	<div id="result"></div>
</body>
</html>