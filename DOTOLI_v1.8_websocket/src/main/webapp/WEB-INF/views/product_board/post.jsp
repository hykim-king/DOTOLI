<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <!DOCTYPE html>
  <html lang="en">
  
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>HTML 기본 문서</title>
    <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
  </head>
  
  <body>
  
    <form>
  
      <div>
        <label for="">상품 사진(최대 6장)</label>
        <input type="file" id="board_images" name="board_images" accept="image/*" multiple>
        <div id="previewImages" style="display: flex; gap: 10px;"></div>
      </div>
  
      <div>
        <label for="board_title">제목 : </label>
        <input type="text" id="board_title" name="board_title">
      </div>
  
      <div>
        <label for="category_id">카테고리 선택 : </label>
        <select id="category_id" name="category_id">
          <option value="100">디지털기기</option>
          <option value="101">생활가전</option>
          <option value="102">가구/인테리어 3</option>
          <option value="103">생활/주방</option>
          <option value="104">유아동</option>
          <option value="105">여성패션/잡화</option>
          <option value="106">남성패션/잡화</option>
          <option value="107">뷰티/미용</option>
          <option value="108">스포츠/레저</option>
          <option value="109">취미/게임/음반</option>
          <option value="110">도서</option>
          <option value="111">티켓/교환권</option>
          <option value="112">가공식품</option>
          <option value="113">건강기능식품</option>
          <option value="114">반려동물용품</option>
          <option value="115">식물</option>
          <option value="116">기타 중고 물품</option>
        </select>
      </div>
  
      <div>
        <label for="price">가격 : </label>
        <input type="text" id="price" name="price">
      </div>
  
      <div>
        <label for="board_content">설명글</label>
        <textarea name="board_content" id="board_content"></textarea>
      </div>
  
      <div>
        <label for="">거래 방식</label>
        <input type="radio" id="trade_0" name="how_trade" value="0">
        <label for="trade_0">직거래</label>
        <input type="radio" id="trade_1" name="how_trade" value="1">
        <label for="trade_1">택배거래</label>
      </div>
  
      <div>
        <label for="address">주소</label>
        <div class="input-group">
          <input type="text" id="trade_address" name="trade_address" placeholder="주소를 입력하세요." readonly required>
          <input class="" type="button" onclick="sample5_execDaumPostcode()" value="주소 검색" id="addressBtn">
        </div>
      </div>
  
      <button id="postBtn">등록하기</button>
  
    </form>
  
  
    <!-- 카카오 맵 주소 API -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6494369aebd01baf926e57a1affa1288&libraries=services"></script>
    <script>
      function sample5_execDaumPostcode() {
        new daum.Postcode({
          oncomplete: function (data) {
            var addr = data.address; // 최종 주소 변수
  
            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("trade_address").value = addr;
  
          }
        }).open();
      }
    </script>
    <script>
  
      // 이미지 미리보기
      document.getElementById('board_images').addEventListener("change", function (event) {
  
        const files = event.target.files;
        const previewContainer = document.getElementById("previewImages");
        previewContainer.innerHTML = ""; //기존 미리보기 이미지 제거
  
  
  
        // files 순회
        Array.from(files).forEach((file) => {
          const reader = new FileReader();
          reader.onload = function (e) {
            const imgElement = document.createElement("img");
            imgElement.src = e.target.result;
            imgElement.alt = "미리보기 이미지";
            imgElement.style.width = "100px";
            imgElement.style.height = "100px";
            previewContainer.appendChild(imgElement);
  
  
          };
          reader.readAsDataURL(file);
  
        }); // files 순회 -- END
  
      }); // 이미지 미리보기 -- END
    </script>
  
  
    <script>
      // 게시글 등록 ajax 요청
      $('#postBtn').on("click", function (event) {
  
        event.preventDefault();
  
        const maxFileSize = 5 * 1024 * 1024; // 최대 파일 크기 5MB (5MB = 5 * 1024 * 1024 bytes)
        const files = document.getElementById('board_images').files;

        const formData = new FormData();

        // 라디오 버튼에서 선택된 값 가져오기
        const howTrade = $('input[name="how_trade"]:checked').val(); 
  
        if (files.length === 0 || files.length > 6) {
          alert("이미지를 선택해주세요.");
          return;
        }
  
        // 여러 이미지를 서버에 전송
        Array.from(files).forEach((file) => {
          if (file.size > maxFileSize) {
            alert("파일 크기는 최대 5MB 이하입니다.")
            return;
          }
          formData.append("board_Images", file) // 이미지 파일을 FormDate에 추가
        });

        formData.append("board_Title", $('#board_title').val());
        formData.append("category_Id", $('#category_id').val());
        formData.append("price", $('#price').val());
        formData.append("board_Content", $('#board_content').val());
        formData.append("how_Trade", howTrade);
        formData.append("trade_Address", $('#trade_address').val());
  
        $.ajax({
          url: '/ehr/product_board/post.do',
          method: 'POST',
          processData: false,
          contentType: false,
          data: formData,
          success: function (response) {
            console.log(response.messageId + response.message);
            if (response.messageId === 1) {
              alert(response.message);
              window.location.href = '/ehr/main.do' // 페이지 새로고침
            } else {
              alert(response.message);
            }
          },
          error: function (xhr, status, error) {
            console.error("Error Status: ", xhr.status);
            console.error("Error Response: ", xhr.responseText);
            alert('프로필 사진 업로드 중 오류가 발생했습니다.');
          }
        }); // ajax 요청 -- END
  
      }); // 이미지 업데이트 ajax 요청 -- END
    </script>
  </body>
  
  </html>