const weeks = ['日', '月', '火', '水', '木', '金', '土'];
const todayDate = new Date();
let year = todayDate.getFullYear();
let month = todayDate.getMonth() + 1;
const config = { show: 2 };

let checkinDateValue = ""; // チェックイン日保持
let checkoutDateValue = ""; // チェックアウト日保持
let currentTarget = "checkin"; // 今どちらを編集中か

//カレンダーの表示
function showCalendar(year, month) {
	document.querySelector('#calendar').innerHTML = ''; // リセット（複数月対応）
	for (let i = 0; i < config.show; i++) {
		const calendarHtml = createCalendar(year, month);
		const sec = document.createElement('section');
		sec.innerHTML = calendarHtml;
		document.querySelector('#calendar').appendChild(sec);
		month++;
		if (month > 12) {
			year++;
			month = 1;
		}
	}
}

// カレンダーの作成
function createCalendar(year, month) {
	const startDate = new Date(year, month - 1, 1); //今月の1日
	const endDate = new Date(year, month, 0); //今月の末日
	const endDayCount = endDate.getDate(); //最終日の日付のみ
	const startDay = startDate.getDay(); //始まりの曜日
	let dayCount = 1;
	let calendarHtml = '';
	calendarHtml += '<h1>' + year + '/' + month + '</h1>';
	calendarHtml += '<table>';

	//曜日の作成
	for (let i = 0; i < weeks.length; i++) {
		calendarHtml += '<td class="week">' + weeks[i] + '</td>';
	}

	//日付の作成
	for (let w = 0; w < 6; w++) { //6行分の作成
		calendarHtml += '<tr>';

		for (let d = 0; d < 7; d++) {

			//その月のカレンダーの空白部分は非表示
			if (w == 0 && d < startDay) {
				calendarHtml += '<td>　</td>';
			} else if (dayCount > endDayCount) {
				calendarHtml += '<td>　</td>';
				dayCount++;
			} else {
				const dateStr = `${year}-${String(month).padStart(2, "0")}-${String(dayCount).padStart(2, "0")}`; //LocalDate型のフォーマットに値を合わせる。
				let isDisabled = false; //グレーアウト用のクラス
				const thisDate = new Date(year, month - 1, dayCount);

				// 今日より前は常に不可
				if (thisDate < new Date(todayDate.getFullYear(), todayDate.getMonth(), todayDate.getDate())) {
					isDisabled = true;
				}

				// チェックアウト選択中かつチェックイン日ありなら「チェックインから7日後以降」不可、「チェックイン未満」も不可
				if (!isDisabled && currentTarget === "checkout" && checkinDateValue) {
					const minDate = new Date(checkinDateValue);
					const maxDate = new Date(checkinDateValue);
					maxDate.setDate(minDate.getDate() + 7);
					if (thisDate < minDate || thisDate > maxDate) {
						isDisabled = true;
					}
				}


				// チェックイン・アウトがセットされてる時だけ色を付ける。
				let selectedClass = "";
				if (checkinDateValue && dateStr === checkinDateValue) {
					selectedClass = " selected-checkin";
				} else if (checkoutDateValue && dateStr === checkoutDateValue) {
					selectedClass = " selected-checkout";
				} else if (checkinDateValue && checkoutDateValue && new Date(dateStr) > new Date(checkinDateValue) && new Date(dateStr) < new Date(checkoutDateValue)) {
					selectedClass = " selected-between";
				}


				// チェックイン選択中・またはどちらも未入力なら普通に押せる
				if (isDisabled) {
					calendarHtml += `<td class="calendar_td is-disabled ${selectedClass}" data-date="${dateStr}">${dayCount}</td>`;
				} else {
					calendarHtml += `<td class="calendar_td ${selectedClass}" data-date="${dateStr}">${dayCount}</td>`;
				}
				dayCount++;
			}
		}
		calendarHtml += '</tr>';
	}
	calendarHtml += '</table>';
	return calendarHtml;
}

// カレンダー移動
function moveCalendar(eventMove) {
	//前ボタンが押されたら、又は、今年でかつ今月じゃない時
	if (eventMove.target.id === 'prev' && !(year === todayDate.getFullYear() && month === todayDate.getMonth() + 1)) {
		month--;
		if (month < 1) {
			year--;
			month = 12;
		}
	}
	//もし次ボタンが押されたら
	if (eventMove.target.id === 'next') {
		month++;
		if (month > 12) {
			year++;
			month = 1;
		}
	}
	showCalendar(year, month);
}

document.querySelector('#next').addEventListener('click', moveCalendar);
document.querySelector('#prev').addEventListener('click', moveCalendar);


//inputクリックで、カレンダーの表示アクションが実行
// カレンダー表示の際、入力欄をグレーアウト
const checkinInput = document.querySelector("#checkinDate");
const checkoutInput = document.querySelector("#checkoutDate");
const calendarArea = document.getElementById("calendarArea");

function openCalendar(type) {
	currentTarget = type;
	//チェックインが入力されるときは必ずチェックアウトの値を削除
	checkinInput.classList.add("input-active");
	checkoutInput.classList.add("input-active");
	calendarArea.style.display = "block";
	showCalendar(year, month);
}

function closeCalendar() {
	checkinInput.classList.remove("input-active");
	checkoutInput.classList.remove("input-active");
	calendarArea.style.display = "none";
}

checkinInput.addEventListener('click', function() { openCalendar("checkin"); });
checkoutInput.addEventListener('click', function() { openCalendar("checkout"); });
document.getElementById("closeCalendar").addEventListener('click', closeCalendar);



//閉じるボタンが押されたとき
document.getElementById("closeCalendar").addEventListener('click', function() {
	document.getElementById("calendarArea").style.display = "none";
});

// 日付クリックでinputへ
document.addEventListener("click", function(event) {
	if (event.target.classList.contains("calendar_td") && !event.target.classList.contains("is-disabled")) {
		const selectedDate = event.target.dataset.date;
		const checkinVal = document.getElementById("checkinDate").value;
		const checkoutVal = document.getElementById("checkoutDate").value;

		
		if (currentTarget === "checkin") {
			// ★必ずアウト欄を消す
			document.getElementById("checkoutDate").value = "";
			checkoutDateValue = "";

			document.getElementById("checkinDate").value = selectedDate;
			checkinDateValue = selectedDate;
			currentTarget = "checkout";
		} else {
			// チェックアウト選択時：チェックインが未設定またはチェックアウトがチェックイン以前ならチェックインの欄上書き
			if (!checkinVal || new Date(selectedDate) <= new Date(checkinVal)) {
				document.getElementById("checkinDate").value = selectedDate;
				checkinDateValue = selectedDate;
				currentTarget = "checkout";
			} else {
				document.getElementById("checkoutDate").value = selectedDate;
				checkoutDateValue = selectedDate;
				currentTarget = "checkin";
			}
		}
		// 日付選択ごとに再描画（チェックアウトの時は「7日先以降グレーアウト」更新のため）
		showCalendar(year, month);
	}
});

showCalendar(year, month);