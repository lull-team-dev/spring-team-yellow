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