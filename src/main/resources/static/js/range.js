// ------------- 変数の取得（スライダー・テキスト・hiddenなど全要素） -------------
const underSlider = document.getElementById("pi_under_input");       // 下限スライダー
const upSlider = document.getElementById("pi_up_input");             // 上限スライダー
const underHidden = document.getElementById("under_value_input");    // 下限 hidden（フォーム送信用）
const upHidden = document.getElementById("up_value_input");          // 上限 hidden（フォーム送信用）
const underDisplay = document.getElementById("under_value_display"); // 下限テキスト入力欄
const upDisplay = document.getElementById("up_value_display");       // 上限テキスト入力欄
const sliderRange = document.getElementById("slider-range");         // スライダーのバー部分

// ------------- 数値をカンマ付き文字列にフォーマット -------------
function formatWithComma(n) {
	return n.toLocaleString();
}

// ------------- カンマを除去し数値化 -------------
function uncomma(str) {
	return parseInt(str.replace(/,/g, '')) || 0;
}

// ------------- スライダー操作時に値と表示を更新 -------------
function updateFromSlider() {
	let lower = parseInt(underSlider.value); // 下限スライダーの値
	let upper = parseInt(upSlider.value);    // 上限スライダーの値

	// 上限が1000円以下の場合は強制的に1000円に補正
	if (upper <= 1000) {
		upper = 1000;
		upSlider.value = upper;
	}

	// 下限が上限以上にならないよう補正（最低0円）
	if (lower >= upper) {
		lower = upper - 1000;
		if (lower < 0) lower = 0;
		underSlider.value = lower;
	}

	// hidden・テキスト欄も同期
	underHidden.value = lower;
	upHidden.value = upper;
	underDisplay.value = formatWithComma(lower);
	upDisplay.value = formatWithComma(upper);

	// スライダーの選択範囲バーの位置・幅を調整
	const percentLeft = (lower / 100000) * 100;
	const percentRight = (upper / 100000) * 100;
	sliderRange.style.left = percentLeft + "%";
	sliderRange.style.width = (percentRight - percentLeft) + "%";
}

// ------------- テキスト入力欄の値をスライダー・hiddenに反映 -------------
function updateFromTextInput() {
	let lower = uncomma(underDisplay.value); // 下限テキスト欄の値
	let upper = uncomma(upDisplay.value);    // 上限テキスト欄の値

	// 上限が0円以下なら1000円に補正
	if (upper <= 0) {
		upper = 1000;
	}
	// 下限がマイナスなら0円に補正
	if (lower < 0) {
		lower = 0;
	}
	// 下限が上限以上の場合は補正
	if (lower >= upper) {
		lower = upper - 1000;
		if (lower < 0) lower = 0;
	}

	// スライダーとhidden欄を同期
	underSlider.value = lower;
	upSlider.value = upper;
	underHidden.value = lower;
	upHidden.value = upper;

	// テキスト欄も再整形
	underDisplay.value = formatWithComma(lower);
	upDisplay.value = formatWithComma(upper);

	// スライダーのバーも更新
	updateFromSlider();
}

// ------------- イベント登録 ---------------

// --- スライダー操作時、表示・hiddenも即時反映 ---
underSlider.addEventListener("input", updateFromSlider);
upSlider.addEventListener("input", updateFromSlider);

// --- テキスト欄の値が変わったとき（リアルタイム反映。不要ならコメントアウト可） ---
// underDisplay.addEventListener("input", updateFromTextInput);
// upDisplay.addEventListener("input", updateFromTextInput);

// --- テキスト欄でEnter押下時のみ反映（この行が「入力確定」用） ---
underDisplay.addEventListener("keydown", function(e) {
	if(e.key === "Enter") {
		updateFromTextInput();
		underDisplay.blur(); // フォーカス外して確定
	}
});
upDisplay.addEventListener("keydown", function(e) {
	if(e.key === "Enter") {
		updateFromTextInput();
		upDisplay.blur();
	}
});

// --- テキスト欄からフォーカスを外した時にも反映（カーソル外し対応） ---
underDisplay.addEventListener("blur", updateFromTextInput);
upDisplay.addEventListener("blur", updateFromTextInput);

// --- テキスト欄にフォーカスしたときはカンマを除去＆全選択 ---
underDisplay.addEventListener("focus", () => {
	underDisplay.value = uncomma(underDisplay.value);
	underDisplay.select();
});
upDisplay.addEventListener("focus", () => {
	upDisplay.value = uncomma(upDisplay.value);
	upDisplay.select();
});

// ------------- ページ読み込み時の初期化処理 -------------
window.addEventListener("DOMContentLoaded", () => {
	const defaultUnder = parseInt(underHidden.value || 0);        // 下限初期値
	const defaultUp = parseInt(upHidden.value || 100000);         // 上限初期値

	underSlider.value = defaultUnder;
	upSlider.value = defaultUp;

	underDisplay.value = formatWithComma(defaultUnder);
	upDisplay.value = formatWithComma(defaultUp);

	updateFromSlider(); // スライダー/バー/hiddenすべて同期
});
