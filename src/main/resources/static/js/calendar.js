// ========================== カレンダーJS ==========================

// 曜日配列・今日の日付
const weeks = ['日', '月', '火', '水', '木', '金', '土'];
const todayDate = new Date();
let year = todayDate.getFullYear();
let month = todayDate.getMonth() + 1;
const config = { show: 2 };

let checkinDateValue = ""; // チェックイン日
let checkoutDateValue = ""; // チェックアウト日
let currentTarget = "checkin"; // 今どちらを編集中か

// ========== カレンダー本体を生成 ==========
function showCalendar(year, month) {
  document.querySelector('#calendar').innerHTML = '';
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
  // 前月ボタンの制御（今月だけはhidden）
  const isCurrentMonth = (year === todayDate.getFullYear() && (month - config.show) === todayDate.getMonth() + 1);
  document.getElementById("prev").disabled = isCurrentMonth;
}

// ========== 1ヶ月分のカレンダーHTML ==========
function createCalendar(year, month) {
  const startDate = new Date(year, month - 1, 1);
  const endDate = new Date(year, month, 0);
  const endDayCount = endDate.getDate();
  const startDay = startDate.getDay();
  let dayCount = 1;
  let calendarHtml = '';
  calendarHtml += '<h1 style="text-align:center; font-size:1.6em; font-weight:bold; margin-bottom:16px; margin-top:0;">' + year + '/' + month + '</h1>';
  calendarHtml += '<table style="margin:0 auto;">';
  for (let i = 0; i < weeks.length; i++) {
    calendarHtml += '<td class="week">' + weeks[i] + '</td>';
  }
  for (let w = 0; w < 6; w++) {
    calendarHtml += '<tr>';
    for (let d = 0; d < 7; d++) {
      if (w == 0 && d < startDay) {
        calendarHtml += '<td>　</td>';
      } else if (dayCount > endDayCount) {
        calendarHtml += '<td>　</td>';
        dayCount++;
      } else {
        const dateStr = `${year}-${String(month).padStart(2, "0")}-${String(dayCount).padStart(2, "0")}`;
        let isDisabled = false;
        const thisDate = new Date(year, month - 1, dayCount);
        if (thisDate < new Date(todayDate.getFullYear(), todayDate.getMonth(), todayDate.getDate())) {
          isDisabled = true;
        }
        if (!isDisabled && currentTarget === "checkout" && checkinDateValue) {
          const minDate = new Date(checkinDateValue);
          const maxDate = new Date(checkinDateValue);
          maxDate.setDate(minDate.getDate() + 7);
          if (thisDate < minDate || thisDate > maxDate) {
            isDisabled = true;
          }
        }
        let selectedClass = "";
        if (checkinDateValue && dateStr === checkinDateValue) {
          selectedClass = " selected-checkin";
        } else if (checkoutDateValue && dateStr === checkoutDateValue) {
          selectedClass = " selected-checkout";
        } else if (checkinDateValue && checkoutDateValue && new Date(dateStr) > new Date(checkinDateValue) && new Date(dateStr) < new Date(checkoutDateValue)) {
          selectedClass = " selected-between";
        }
        if (isDisabled) {
          calendarHtml += `<td class="calendar_td is-disabled${selectedClass}" data-date="${dateStr}">${dayCount}</td>`;
        } else {
          calendarHtml += `<td class="calendar_td${selectedClass}" data-date="${dateStr}">${dayCount}</td>`;
        }
        dayCount++;
      }
    }
    calendarHtml += '</tr>';
  }
  calendarHtml += '</table>';
  return calendarHtml;
}

// ========== 前月・次月ナビ ==========
function moveCalendar(eventMove) {
  if (eventMove.target.id === 'prev') {
    if (!(year === todayDate.getFullYear() && month === todayDate.getMonth() + 1)) {
      month--;
      if (month < 1) {
        year--;
        month = 12;
      }
    }
  }
  if (eventMove.target.id === 'next') {
    month++;
    if (month > 12) {
      year++;
      month = 1;
    }
  }
  showCalendar(year, month);
}

// ナビボタン
document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('next').addEventListener('click', moveCalendar);
  document.getElementById('prev').addEventListener('click', moveCalendar);
});

// ========== inputクリックでカレンダー表示 ==========
const checkinInput = document.querySelector("#checkinDate");
const checkoutInput = document.querySelector("#checkoutDate");
const calendarArea = document.getElementById("calendarArea");

function openCalendar(type) {
  currentTarget = type;
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

// ========== 日付クリックでinputへ ==========
document.addEventListener("click", function(event) {
  // カレンダー外クリックで閉じる
  if (calendarArea.style.display === "block" && !calendarArea.contains(event.target) && event.target !== checkinInput && event.target !== checkoutInput) {
    closeCalendar();
  }
  // 日付クリックで値セット
  if (event.target.classList.contains("calendar_td") && !event.target.classList.contains("is-disabled")) {
    const selectedDate = event.target.dataset.date;
    const checkinVal = document.getElementById("checkinDate").value;
    const checkoutVal = document.getElementById("checkoutDate").value;
    if (currentTarget === "checkin") {
      document.getElementById("checkoutDate").value = "";
      checkoutDateValue = "";
      document.getElementById("checkinDate").value = selectedDate;
      checkinDateValue = selectedDate;
      currentTarget = "checkout";
    } else {
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
    showCalendar(year, month);
  }
});
showCalendar(year, month);
