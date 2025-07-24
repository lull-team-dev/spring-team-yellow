/**
 * 遊び心
 */
function playGifOnce() {
	const logo = document.getElementById("kapibaraLogo");
	const staticImg = "/uploads/images/kapibara.png";
	const gifImg = "/uploads/gifs/kapibara.gif";

	logo.src = gifImg + '?t=' + new Date().getTime(); // キャッシュ対策

	setTimeout(() => {
		logo.src = staticImg;
	}, 1500); // GIFの長さに応じて調整
}

function playGifEat() {
	const gifWrapper = document.querySelector('.footer-logo-gif');
	const gifImg = gifWrapper.querySelector('#kapibaraEat');

	// 表示状態にする
	gifWrapper.classList.add('active');

	// GIFをリセットするためにsrcを付け直す
	const currentSrc = gifImg.src;
	gifImg.src = '';
	gifImg.src = currentSrc;

	// GIFの長さ（例：3秒）に合わせて非表示にする
	setTimeout(() => {
		gifWrapper.classList.remove('active');
	}, 1600); // GIFの再生時間に応じて調整
}

// にんじんボタンにイベント追加
document.getElementById('carrot').addEventListener('click', playGifEat);

