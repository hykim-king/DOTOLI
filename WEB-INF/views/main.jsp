<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>당신 근처의 도토리</title>
    <link rel="stylesheet" href="/ehr/resources/assets/css/main.css" />
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
    />
    <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
  </head>
  <body>
    <header>
      <div class="container">
        <img src="/ehr/resources/assets/images/headerBanner.png" class="headerBanner" />
      </div>

      <div id="header">
        <img src="/ehr/resources/assets/images/logo.svg" class="headerImg" />
        <!-- 검색창 + 키워드 코드 입력란 -->
        <div class="mainSector">
          <!-- 검색창 시작 -->
          <form>
            <div class="dropdown">
              <button id="dropdownBtn">중고거래</button>
              <span class="material-symbols-outlined dropdownBtnImg">
                arrow_drop_down
              </span>
              <!-- 중고거래 드롭다운 버튼 옆에 있는 막대기 -->
              <div class="row-hr"></div>
              <input type="text" placeholder="검색어를 입력해주세요" />
              <span class="material-symbols-outlined search-img"> search </span>
            </div>
            <!-- 인기 검색어 -->
            <p class="keyword">
              인기 검색어
              <a href="#">아이폰</a>
              <a href="#">의자</a>
              <a href="#">자전거</a>
              <a href="#">컴퓨터</a>
              <a href="#">책상</a>
              <a href="#">소파</a>
              <a href="#">원피스</a>
              <a href="#">전기자전거</a>
              <a href="#">식탁</a>
              <a href="#">모니터</a>
            </p>
          </form>

          <!-- 검색창 끝 -->
        </div>
        <!-- 검색창 + 키워드 코드 입력란 끝 -->

    <button class="headerBtn" onclick="location.href='<c:url value="/user/login/login.do" />'">로그인</button>
    <button class="headerBtn" onclick="location.href='<c:url value="/user/login/register.do" />'">회원가입</button>
    <button class="headerBtn"><a href="#">마이페이지</a></button>
      </div>
    </header>

    <!-- 당근 배너 임의로 가져온 상태 (추후 변경) -->
    <div class="banner"><img src="/ehr/resources/assets/images/banner.png" /></div>
    <div>
      <h2>카테고리</h2>
      <!-- 슬라이드 코드 시작 -->
      <div class="carousel_container">
        <div class="carousel_wrapper">
          <!-- 첫 번째 슬라이드 -->
          <div class="carousel_item">
            <div class="kategoryWrap">
              <img src="/ehr/resources/assets/images/camera.png" alt="카메라" class="kategory" />
              <img
                src="/ehr/resources/assets/images/microwave.png"
                alt="전자레인지"
                class="kategory"
              />
              <img src="/ehr/resources/assets/images/chair.png" alt="의자" class="kategory" />
              <img src="/ehr/resources/assets/images/pot.png" alt="냄비" class="kategory" />
              <img src="/ehr/resources/assets/images/toy.png" alt="인형" class="kategory" />
            </div>
            <div class="kategoryWrap kategoryH">
              <a href="#"><strong>디지털기기</strong></a>
              <a href="#"><strong>생활가전</strong></a>
              <a href="#"><strong>가구/인테리어</strong></a>
              <a href="#"><strong>생활/주방</strong></a>
              <a href="#"><strong>유아동</strong></a>
            </div>
          </div>

          <!-- 두 번째 슬라이드 이미지 추가후 경로 수정 -->
          <div class="carousel_item">
            <div class="kategoryWrap">
              <img
                src="/ehr/resources/assets/images/childBook.png"
                alt="유아도서"
                class="kategory"
              />
              <img
                src="/ehr/resources/assets/images/fclothes.png"
                alt="여성의류"
                class="kategory"
              />
              <img src="/ehr/resources/assets/images/bag.png" alt="여성잡화" class="kategory" />
              <img
                src="/ehr/resources/assets/images/mclothes.png"
                alt="남성패션/잡화"
                class="kategory"
              />
              <img
                src="/ehr/resources/assets/images/fashion.png"
                alt="뷰티/미용"
                class="kategory"
              />
            </div>
            <div class="kategoryWrap kategoryH">
              <a href="#"><strong>유아도서</strong></a>
              <a href="#"><strong>여성의류</strong></a>
              <a href="#"><strong>여성잡화</strong></a>
              <a href="#"><strong>남성패션/잡화</strong></a>
              <a href="#"><strong>뷰티/미용</strong></a>
            </div>
          </div>

          <!-- 세 번째 슬라이드 이미지 추가후 경로 수정 -->
          <div class="carousel_item">
            <div class="kategoryWrap">
              <img src="/ehr/resources/assets/images/ball.png" alt="스포츠/레저" class="kategory" />
              <img
                src="/ehr/resources/assets/images/game.png"
                alt="취미/게임/음반"
                class="kategory"
              />
              <img src="/ehr/resources/assets/images/book.png" alt="도서" class="kategory" />
              <img src="/ehr/resources/assets/images/ticket.png" alt="티켓/교환권" class="kategory" />
              <img
                src="/ehr/resources/assets/images/second_bear.png"
                alt="가공식품"
                class="kategory"
              />
            </div>
            <div class="kategoryWrap kategoryH">
              <a href="#"><strong>스포츠/레저</strong></a>
              <a href="#"><strong>취미/게임/음반</strong></a>
              <a href="#"><strong>도서</strong></a>
              <a href="#"><strong>티켓/교환권</strong></a>
              <a href="#"><strong>가공식품</strong></a>
            </div>
          </div>

          <!-- 네 번째 슬라이드 이미지 추가후 경로 수정 -->
          <div class="carousel_item">
            <div class="kategoryWrap">
              <img src="/ehr/resources/assets/images/chair.png" alt="노트북2" class="kategory" />
              <img
                src="/ehr/resources/assets/images/second_microwave.png"
                alt="전자레인지2"
                class="kategory"
              />
              <img src="/ehr/resources/assets/images/second_chair.png" alt="의자2" class="kategory" />
              <img src="/ehr/resources/assets/images/second_pot.png" alt="냄비2" class="kategory" />
            </div>
            <div class="kategoryWrap kategoryH">
              <a href="#"><strong>건강기능식품</strong></a>
              <a href="#"><strong>반려동물용품</strong></a>
              <a href="#"><strong>식물</strong></a>
              <a href="#"><strong>기타 중고물품</strong></a>
            </div>
          </div>

          <!--아래 div 태그가 캐러셀 끝-->
        </div>
      </div>
      <!-- 캐러셀 버튼 -->
      <div class="carousel_button_container">
        <button class="carousel_prev">&lt;</button>
        <button class="carousel_next">&gt;</button>
      </div>

      

    <footer>
      <div><strong>도토리 마켓</strong></div>
      <div><strong>Copyright &copy; DOTOLI</strong></div>
      <div><strong>주소</strong> 서울 마포구 양화로 122 3층, 4층</div>
      <div><strong>고객문의</strong> abc@email.com</div>
    </footer>

  <!-- 임시 버튼 -->
  <div>
    <button id="logoutBtn" class="headerBtn">로그아웃</button>
  </div>

    <!-- 캐러셀 js -->
    <script src="/ehr/resources/assets/js/main.js"></script>

  <!-- 로그 아웃 -->
  <script>
    $('#logoutBtn').on('click', function (event) {

      event.preventDefault();

      // http 요청
      $.ajax({
        url: '/ehr/user/login/logout.do',
        method: 'POST',
        contentType: 'application/json',
        success: function (response) {
          if(response.message === 0)
          alert(response.message);
          window.location.href = "/ehr/main.do"
        },
        error: function () {
          $('#loginMessage').text(response.message);
        }
      }); // http 요청 -- end

    }) // 로그아웃 -- end

  </script>

  </body>
</html>

