//https://qiita.com/kan_dai/items/b1850750b883f83b9bee　参照

const weeks = ['日', '月', '火', '水', '木', '金', '土'] // 曜日の配列
const date = new Date() // 現在の日付情報を取得
let year = date.getFullYear() // 今年（例：2025）
let month = date.getMonth() + 1 // 今月（例：6月 → 6。JSは0から始まるので+1が必要）
const config = {
	show: 2, // 何ヶ月分カレンダーを表示するか
}


function showCalendar(year, month) {
	for (i = 0; i < config.show; i++) {
		const calendarHtml = createCalendar(year, month)
		const sec = document.createElement('section')
		sec.innerHTML = calendarHtml
		document.querySelector('#calendar').appendChild(sec)
		month++
		if (month > 12) {
			year++
			month = 1
		}
	}
}

function createCalendar(year, month) {
	const startDate = new Date(year, month - 1, 1) // 月の最初の日を取得
	const endDate = new Date(year, month, 0) // 月の最後の日を取得
	const endDayCount = endDate.getDate() // 月の末日のみ
	const startDay = startDate.getDay() // 月の最初の日の曜日を取得
	let dayCount = 1 // 日にちのカウント
	let calendarHtml = '' // HTMLを組み立てる変数
	const todayDate = new Date(); //本日の取得
	const todayStr = `${todayDate.getFullYear()}-${todayDate.getMonth() + 1}-${todayDate.getDate()}`;

	calendarHtml += '<h1>' + year + '/' + month + '</h1>'
	calendarHtml += '<table>'
	// 曜日の行を作成
	for (let i = 0; i < weeks.length; i++) {
		calendarHtml += '<td class="week">' + weeks[i] + '</td>'
	}

	for (let w = 0; w < 6; w++) {
		calendarHtml += '<tr>'
		for (let d = 0; d < 7; d++) {
			if (w == 0 && d < startDay) {
				// 1行目で1日の曜日の前
				calendarHtml += '<td>　</td>'
			} else if (dayCount > endDayCount) {
				// 末尾の日数を超えた
				calendarHtml += '<td>　</td>'
				dayCount++
			} else {
				// 生成している日付
				const dateStr = `${year}-${month}-${dayCount}`;
				// 過去ならis-disabledクラス付与
				const isPast = new Date(year, month - 1, dayCount) < new Date(todayDate.getFullYear(), todayDate.getMonth(), todayDate.getDate());
				if (isPast) {
					calendarHtml += `<td class="calendar_td is-disabled" data-date="${dateStr}">${dayCount}</td>`;
				} else {
					calendarHtml += `<td class="calendar_td" data-date="${dateStr}">${dayCount}</td>`;
				}
				dayCount++;
			}
		}
		calendarHtml += '</tr>'
		//余計な空白の排除
		//if (dayCount > endDayCount) break;
	}

	calendarHtml += '</table>'
	return calendarHtml
}

function moveCalendar(eventMove) {
	document.querySelector('#calendar').innerHTML = ''

	if (eventMove.target.id === 'prev') {
		month--

		if (month < 1) {
			year--
			month = 12
		}
	}

	if (eventMove.target.id === 'next') {
		month++

		if (month > 12) {
			year++
			month = 1
		}
	}

	showCalendar(year, month)
}

document.querySelector('#next').addEventListener('click', moveCalendar)
document.querySelector('#prev').addEventListener('click', moveCalendar)

// 1. どちらのinputを編集中かを管理する変数を用意（追加！）
let currentTarget = "checkin";

// 2. inputクリック時に「どちらが選択されたか」だけをセット（ここがポイント！）
document.querySelector('#checkinDate').addEventListener('click', function() {
	currentTarget = "checkin";
});
document.querySelector('#checkoutDate').addEventListener('click', function() {
	currentTarget = "checkout";
});


//日付クリックでinputタグへデータの挿入
let clickCount = 0;

document.addEventListener("click", function(event) {
	if (event.target.classList.contains("calendar_td") && !event.target.classList.contains("is-disabled")) {
		const selectedDate = event.target.dataset.date;
		clickCount++;

		if (clickCount % 2 === 1 || currentTarget === "checkin") {
			// 奇数回目：checkin
			document.getElementById("checkinDate").value = selectedDate;
		} else {
			// 偶数回目：checkout
			document.getElementById("checkoutDate").value = selectedDate;
		}
	}
})


showCalendar(year, month)
