/**
 * 画像スライドの
 */


let imgs = [];
let num = 0;

function showImage() {
  const img = document.getElementById("room-img");
  img.classList.add("fade-out");

  setTimeout(() => {
    img.src = imgs[num];
    img.classList.remove("fade-out");
  }, 250);
}

function next() {
  num = (num + 1) % imgs.length;
  showImage();
}

function prev() {
  num = (num - 1 + imgs.length) % imgs.length;
  showImage();
}

// 初期化関数（Thymeleafから値を渡すため）
function initSlider(imgList) {
  imgs = imgList;
  showImage();
}


