// ログインのみ
package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Guest;
import com.example.demo.entity.Reservation;
import com.example.demo.model.Account;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.ReservDataRepository;
import com.example.demo.repository.ReservationRepository;

@Controller
public class ReservationHistoryController {
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	ReservDataRepository reservDataRepository;
	@Autowired
	GuestRepository guestRepository;
	@Autowired
	Account account;

	@GetMapping("/reservationHistory")
	public String showReservationHistory(Model model) {

		Guest guest = guestRepository.findById(account.getId()).get();
		List<Reservation> reservationHistorys = reservationRepository.findByGuest(guest);

		model.addAttribute("reservationHistorys", reservationHistorys);
		return "reservation-history";
	}

	//
	@GetMapping("/reservationHistory/{id}")
	public String detailsReservationHistory(
			@PathVariable("id") Integer id,
			@RequestParam(required = false) boolean afterReserve,
			Model model) {

		Guest guest = guestRepository.findById(account.getId()).get();
		Reservation reservationHistory = reservationRepository.findById(id).get();

		//チェックアウト日を計算
		LocalDate stayDate = reservationHistory.getStayDate(); //チェックイン
		Integer stayNights = reservationHistory.getStayNights(); //宿泊日数
		LocalDate checkoutDate = stayDate.plusDays(stayNights);//チェックアウト

		//画像取得
		List<String> imgList = List.of(reservationHistory.getRoom().getImgPath(),
				reservationHistory.getRoom().getImgPath2());

		model.addAttribute("checkoutDate", checkoutDate);
		model.addAttribute("reservationHistory", reservationHistory);
		model.addAttribute("imgList", imgList);

		if (afterReserve) {
			model.addAttribute("afterReserveText", "予約が完了しました");
		} else {
			model.addAttribute("afterReserveText", "予約詳細");
		}

		return "reservation-detail";
	}

}
