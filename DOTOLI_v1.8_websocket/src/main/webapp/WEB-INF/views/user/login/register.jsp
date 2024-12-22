<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>HTML 기본 문서</title>
      <link rel="stylesheet" href="/ehr/resources/assets/css/register.css" />
      <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
    </head>

    <body>

      <div>
        <h1>회원가입</h1>
      </div>

      <form id="registerForm" method="POST" action="">
        <h1 class="logo">
          <a href="/ehr/main.do"><img src="/ehr/resources/assets/images/logo.svg" alt="도토리"></a>
        </h1>

        <!-- 이메일 입력 및 중복 확인 -->
        <div class="form">
          <label for="auth-code">이메일(아이디)</label>
          <input type="signinput" id="email" name="email" placeholder="이메일" minlength="3" maxlength="320" required>
          <span id="emailMessage"></span>
        </div>

        <div class="form">
          <label for="auth-code">인증코드</label>
          <div class="input-group">
            <input type="text" id="auth-code" name="auth_code" placeholder="인증코드" minlength="6" maxlength="6"
              disabled />
            <button type="button" id="sendAuthBtn">발송</button>
          </div>
          <span id="authMessage"></span>
        </div>

        <!-- 나머지 회원가입 정보 입력 -->
        <div class="form2">
          <label for="auth-code">이름</label>
          <input type="signinput" id="name" name="name" placeholder="이름을 입력해주세요." minlength="2" maxlength="7" required>
        </div>

        <div class="form2">
          <label for="auth-code">전화번호</label>
          <input type="signinput" id="phone" name="phone" placeholder="전화번호를 입력해주세요.(- 하이픈 제외)" minlength="8"
            maxlength="15" required>
          <span id="phoneMessage"></span>
        </div>

        <div class="form2">
          <label for="auth-code">닉네임</label>
          <input type="signinput" id="nickname" name="nickname" placeholder="닉네임을 입력해주세요." minlength="2" maxlength="12"
            required>
        </div>

        <div class="form2">
          <label for="address">주소</label>
          <div class="input-group">
            <input type="text" id="address" name="address" placeholder="주소" readonly required>
            <input type="button" onclick="sample5_execDaumPostcode()" value="주소 검색" id="sendAuthBtn">
            <div id="map" style="width: 300px; height: 300px; margin-top: 10px; display: none"></div>
          </div>
        </div>

        <div>
          <button id="register" class="button_own" type="submit">회원가입</button>
          <span id="registerMessage"></span>
        </div>

        <div>
          <button id="return_main" class="button_own">로그인으로 돌아가기</button>
        </div>

      </form>

      <br />
      <hr />
      <footer>
        <p>
          도움이 필요하면 <a href="#">이메일</a> 또는 고객센터 1670-2910로 문의 부탁드립니다.
        </p>
        <p>고객센터 운영시간: 09:18시(점심시간 12~13시, 주말/공휴일 제외)</p>
      </footer>

      <!-- 로그인으로 돌아가기 버튼 -->
      <script>
        $('#return_main').on('click', function (event) {
          event.preventDefault();

          let register_cancel = window.confirm('회원 가입을 취소 하시겠습니까?');
          //onclick="location.href='<c:url value="/user/login/login.do" />'"
          if (register_cancel) {
            window.location.href = "/ehr/user/login/login.do";
          }

        }) 
      </script>

      <script>
        // 인증 번호 요청
        $('#sendAuthBtn').on('click', function () {

          const email = $('#email').val();

          // http 요청
          $.ajax({
            url: '/ehr/send_auth/register.do',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email: email }),
            success: function (response) {
              if (response.messageId === 0) {
                $('#emailMessage').text('인증 번호가 발송되었습니다.');
                $('#auth-code').prop('disabled', false);

              } else {
                $('#emailMessage').text('이미 사용 중인 이메일 입니다.');
              }
            },
            error: function () {
              $('#emailMessage').text('인증 번호 요청 중 에러가 발생했습니다.');
            }
          }); // http 요청 -- end
        }); // 인증번호 요청 -- end


        // 회원가입
        $('#register').on('click', function (event) {

          event.preventDefault();

          const formData = {
            email: $('#email').val(),
            auth_code: $('#auth-code').val(),
            name: $('#name').val(),
            phone: $('#phone').val(),
            nickname: $('#nickname').val(),
            address: $('#address').val()
          };

          // http 요청
          $.ajax({
            url: '/ehr/register.do',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
              if (response.messageId === 1) {
                alert(response.message);
                window.location.href = "/ehr/user/login/login.do"
              } else {
                alert(response.message);
              }
            },
            error: function () {
              $('#registerMessage').text('회원가입 중 에러가 발생했습니다.');
            }
          }); // http 요청 -- end
        }); // 회원가입 -- end
      </script>

      <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
      <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6494369aebd01baf926e57a1affa1288&libraries=services"></script>
      <script>

        function sample5_execDaumPostcode() {
          new daum.Postcode({
            oncomplete: function (data) {
              var addr = data.address; // 최종 주소 변수

              // 주소 정보를 해당 필드에 넣는다.
              document.getElementById("address").value = addr;
            }
          }).open();
        }
      </script>
    </body>

    </html>