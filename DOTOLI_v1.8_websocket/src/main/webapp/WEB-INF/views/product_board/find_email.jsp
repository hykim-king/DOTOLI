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




        <button type="submit" id="postBtn">등록하기</button>
      </form>



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

        // 이미지 업데이트 ajax 요청
        $('#postBtn').on("click", function (event) {

          event.preventDefault();

          const files = document.getElementById('board_images').files;
          const formData = new FormData();

          if (files.length === 0 || files.length > 6) {
            alert("이미지를 선택해주세요.");
            return;
          }

          // 여러 이미지를 서버에 전송
          Array.from(files).forEach((file) => {
            formData.append("board_Images", file) // 이미지 파일을 FormDate에 추가
          });

          $.ajax({
            url: '/ehr/product_board/uploadBoard_Images.do',
            method: 'POST',
            processData: false,
            contentType: false,
            data: formData,
            success: function (response) {
              if (response.messageId === 1) {
                alert(response.message);
                window.location.href = '/ehr/main.do' // 페이지 새로고침
              } else {
                alert(response.message);
              }
            },
            error: function () {
              alert('프로필 사진 업로드 중 오류가 발생했습니다.');
            }

          }); // ajax 요청 -- END


        }); // 이미지 업데이트 ajax 요청 -- END
      </script>
    </body>

    </html>