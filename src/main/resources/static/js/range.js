const underSlider = document.getElementById("pi_under_input");
const upSlider = document.getElementById("pi_up_input");
const underHidden = document.getElementById("under_value_input");
const upHidden = document.getElementById("up_value_input");
const underDisplay = document.getElementById("under_value_display");
const upDisplay = document.getElementById("up_value_display");
const sliderRange = document.getElementById("slider-range");

// カンマ付き表示
function formatWithComma(n) {
	return n.toLocaleString();
}

// カンマ除去して数値化
function uncomma(str) {
	return parseInt(str.replace(/,/g, '')) || 0;
}

// スライダーの操作から値・表示を更新
function updateFromSlider() {
	let lower = parseInt(underSlider.value);
	let upper = parseInt(upSlider.value);

	// 上限が0以下なら、強制的に1000円に補正
	if (upper <= 1000) {
		upper = 1000;
		upSlider.value = upper;
	}

	// 下限が上限以上の場合、調整（最低0円）
	if (lower >= upper) {
		lower = upper - 1000;
		if (lower < 0) lower = 0;
		underSlider.value = lower;
	}

	underHidden.value = lower;
	upHidden.value = upper;

	underDisplay.value = formatWithComma(lower);
	upDisplay.value = formatWithComma(upper);

	const percentLeft = (lower / 100000) * 100;
	const percentRight = (upper / 100000) * 100;

	sliderRange.style.left = percentLeft + "%";
	sliderRange.style.width = (percentRight - percentLeft) + "%";
}

// テキスト入力後（blur）にスライダー・hidden更新
function updateFromTextInput() {
	let lower = uncomma(underDisplay.value);
	let upper = uncomma(upDisplay.value);

	if (upper <= 0) {
		upper = 1000;  //最低値を仮に1000円とする
	}
	if (lower < 0) {
		lower = 0;
	}
	if (lower >= upper) {
		lower = upper - 1000;
		if (lower < 0) lower = 0;
	}

	// 値を反映
	underSlider.value = lower;
	upSlider.value = upper;
	underHidden.value = lower;
	upHidden.value = upper;

	underDisplay.value = formatWithComma(lower);
	upDisplay.value = formatWithComma(upper);

	updateFromSlider();
}

// イベント登録
underSlider.addEventListener("input", updateFromSlider);
upSlider.addEventListener("input", updateFromSlider);
underDisplay.addEventListener("blur", updateFromTextInput);
upDisplay.addEventListener("blur", updateFromTextInput);

underDisplay.addEventListener("focus", () => {
	underDisplay.value = uncomma(underDisplay.value);
	underDisplay.select();
});

upDisplay.addEventListener("focus", () => {
	upDisplay.value = uncomma(upDisplay.value);
	upDisplay.select();
});

// 初期化
window.addEventListener("DOMContentLoaded", () => {
	const defaultUnder = parseInt(underHidden.value || 0);
	const defaultUp = parseInt(upHidden.value || 100000);

	underSlider.value = defaultUnder;
	upSlider.value = defaultUp;

	underDisplay.value = formatWithComma(defaultUnder);
	upDisplay.value = formatWithComma(defaultUp);

	updateFromSlider();
});