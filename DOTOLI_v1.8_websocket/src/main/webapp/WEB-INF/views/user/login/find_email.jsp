<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>HTML 기본 문서</title>
  <link rel="stylesheet" href="/ehr/resources/assets/css/find_email.css" />
  <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
</head>

<body>
  <div id="login-wrap">
    <h1 class="logo">
      <a href="/ehr/main.do"><img src="/ehr/resources/assets/images/logo.svg" alt="도토리"></a>
    </h1>

    <form id="registerForm" method="POST" action="">

      <!-- 전화번호 -->
      <div class="form">
        <input type="text" id="email" name="phone" placeholder="전화번호를 입력하세요.(- 하이픈 제외)" minlength="3" maxlength="320" required>
        <span id="emailMessage"></span>
      </div>

      <!-- 이름 -->
      <div class="form">
        <div class="auth-input-wrap">
          <input type="text" id="auth-code" name="name" placeholder="이름을 입력하세요." minlength="2" maxlength="7">
        </div>
        <span id="authMessage"></span>
      </div>


      <!-- 로그인 제출 -->
      <div>
        <button id="login" class="button own" type="submit">이메일 찾기</button>
        <span id="loginMessage"></span>
      </div>

    </form>

    <div>
      <button id="register_btn" class="button own" onclick="location.href='<c:url value="/user/login/login.do" />'">로그인으로 돌아가기</button>
    </div>
    <br />

    <footer class="footer">
      <p><hr><br>&copy; Dotoli Corp. All rights reserved.</p>
      <p>도움이 필요하면 <a href="#">이메일</a> 또는 고객센터 1670-2910로 문의 부탁드립니다.</p>
      <p>고객센터 운영시간: 09:18시(점심시간 12~13시, 주말/공휴일 제외)</p>
      <br>
    </footer>

    <div></div>



    <script>

      // 아이디 찾기
      $('#login').on('click', function (event) {

        event.preventDefault();

        const formData = {
          phone: $('#email').val(),
          name: $('#auth-code').val(),
        };

        // http 요청
        $.ajax({
          url: '/ehr/find_email.do',
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify(formData),
          success: function (response) {
            if (response.messageId === 1) {
              alert("귀하의 이메일은 " + response.message + " 입니다.");
              window.location.href = "/ehr/user/login/login.do"
            } else {
              alert(response.message);
            }
          },
          error: function () {
            alert("이메일 찾기 중 오류가 발생하였습니다.");
          }
        }); // http 요청 -- end
      }); // 회원가입 -- end
    </script>




</body>

</html>