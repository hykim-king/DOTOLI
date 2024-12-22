const carouselWrapper = document.querySelector(".carousel_wrapper");
const prevButton = document.querySelector(".carousel_prev");
const nextButton = document.querySelector(".carousel_next");

let currentIndex = 0; // 현재 슬라이드 인덱스
const totalSlides = document.querySelectorAll(".carousel_item").length;

function moveToSlide(index) {
  // 인덱스 값 계산 (무한 루프 가능하게)
  if (index < 0) {
    currentIndex = totalSlides - 1; // 첫 번째 슬라이드로 이동
  } else if (index >= totalSlides) {
    currentIndex = 0; // 마지막 슬라이드 -> 첫 번째 슬라이드로 이동
  } else {
    currentIndex = index;
  }

  const offset = -currentIndex * 100; // 슬라이드 이동 거리 계산 (각 슬라이드가 100% 크기여서)
  carouselWrapper.style.transform = `translateX(${offset}%)`;
}

// 이전 버튼 이벤트
prevButton.addEventListener("click", () => {
  moveToSlide(currentIndex - 1);
});

// 다음 버튼 이벤트
nextButton.addEventListener("click", () => {
  moveToSlide(currentIndex + 1);
});

moveToSlide(0); // 첫 번째 슬라이드로 이동
