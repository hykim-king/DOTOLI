<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>sync_index^^^</h2>
	<form action="/ehr/sync/sync_result.do" method="post">
		<label for="name">이름:</label> <input type="text" id="name" name="name">
		<input type="submit" value="전송">
	</form>
	${req_name}

</body>
</html>