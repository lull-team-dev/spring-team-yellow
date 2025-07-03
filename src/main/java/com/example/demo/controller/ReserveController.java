package com.example.demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Guest;
import com.example.demo.entity.Plan;
import com.example.demo.entity.ReservData;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Room;
import com.example.demo.model.Account;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;
import com.example.demo.service.TotalPriceService;

@Controller
public class ReserveController {

	@Autowired
	Account account;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	PlanRepository planRepository;
	@Autowired
	GuestRepository guestRepository;
	@Autowired
	ReservationRepository reserveRepository;
	@Autowired
	TotalPriceService totalPriceService;
	@Autowired
	RoomService roomService;

	//予約ホーム画面
	@GetMapping("/rooms/{id}/reserve")
	public String reserveIndex(
			@PathVariable("id") Integer roomId,
			@RequestParam(required = false) Integer planId,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			Model model,
			@ModelAttribute("guestName") String guestName,
			@ModelAttribute("email") String email,
			@ModelAttribute("tel") String tel,
			@ModelAttribute("address") String address) {

		//宿泊予定者情報の取得
		Guest guest = guestRepository.findById(account.getId()).get();
		//部屋の情報取得
		Room room = roomRepository.findById(roomId).get();
		//画像をリストにする
		List<String> imgList = List.of(room.getImgPath(), room.getImgPath2());
		//プラン情報取得
		Plan plan = planRepository.findById(planId).get();

		model.addAttribute("room", room);
		model.addAttribute("plan", plan);
		model.addAttribute("imgList", imgList);
		model.addAttribute("checkinDate", checkinDate);
		model.addAttribute("checkoutDate", checkoutDate);

		//リダイレクトの場合のmodel送信
		if (guestName != null && !guestName.isBlank()) {
			model.addAttribute("guestName", guestName);
		} else {
			model.addAttribute("guestName", guest.getName());
		}

		if (email != null && !email.isBlank()) {
			model.addAttribute("email", email);
		} else {
			model.addAttribute("email", guest.getEmail());
		}

		if (tel != null && !tel.isBlank()) {
			model.addAttribute("tel", tel);
		} else {
			model.addAttribute("tel", guest.getTel());
		}

		if (address != null && !address.isBlank()) {
			model.addAttribute("address", address);
		} else {
			model.addAttribute("address", guest.getAddress());
		}

		return "reserve";
	}

	//予約確認画面
	@PostMapping("/rooms/{id}/reserve")
	public String reserve(
			@PathVariable("id") Integer roomId,
			@RequestParam(required = false) String guestName,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String tel,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) Integer planId,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			Model model) {

		//部屋の情報取得
		Room room = roomRepository.findById(roomId).get();
		//画像をリストにする
		List<String> imgList = List.of(room.getImgPath(), room.getImgPath2());
		//プラン情報取得
		Plan plan = planRepository.findById(planId).get();

		//（プラン料金＋ルーム料金）×宿泊日
		Integer countDay = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);
		Integer TotalPrice = plan.calcTotalPrice(room.getPrice()) * countDay;
		model.addAttribute("countDay", countDay);

		model.addAttribute("guestName", guestName);
		model.addAttribute("email", email);
		model.addAttribute("tel", tel);
		model.addAttribute("address", address);
		model.addAttribute("room", room);
		model.addAttribute("plan", plan);
		model.addAttribute("TotalPrice", TotalPrice);
		model.addAttribute("imgList", imgList);
		model.addAttribute("checkinDate", checkinDate);
		model.addAttribute("checkoutDate", checkoutDate);

		return "reserveConf";
	}

	//予約
	@PostMapping("/rooms/{id}/reserveConf")
	public String reserveConf(
			@PathVariable("id") Integer roomId,
			@RequestParam(required = false) String guestName,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String tel,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) Integer planId,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			@RequestParam(required = false) Integer status,
			RedirectAttributes redirectAttributes) {

		if (status == 2) {
			redirectAttributes.addAttribute("id", roomId);
			redirectAttributes.addAttribute("planId", planId);
			redirectAttributes.addAttribute("checkinDate", checkinDate);
			redirectAttributes.addAttribute("checkoutDate", checkoutDate);
			redirectAttributes.addFlashAttribute("guestName", guestName);
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("tel", tel);
			redirectAttributes.addFlashAttribute("address", address);

			return "redirect:/rooms/{id}/reserve";
		}

		//部屋
		Room room = roomRepository.findById(roomId).get();

		//プラン情報取得
		Plan plan = planRepository.findById(planId).get();

		//宿泊者（アカウント保持者限定）
		Guest guest = guestRepository.findById(account.getId()).get();
		guest.setName(guestName);
		guest.setEmail(email);
		guest.setTel(tel);
		guest.setAddress(address);

		//（プラン料金＋ルーム料金）×宿泊日
		Integer countDay = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);
		Integer dayTotalPrice = plan.calcTotalPrice(room.getPrice());
		Integer totalPrice = dayTotalPrice * countDay;

		//予約の作成
		Reservation reserve = new Reservation(guest, room, plan, totalPrice, countDay, checkinDate);

		//予約詳細の日付細分化
		List<LocalDate> stayDates = roomService.dateCalc(checkinDate, checkoutDate);

		List<ReservData> reservDatas = reserve.getReservDatas();

		for (LocalDate stayDate : stayDates) {
			ReservData reserveData = new ReservData(stayDate, dayTotalPrice, reserve);
			reservDatas.add(reserveData);
		}

		reserveRepository.save(reserve);

		return "reserveComp";
	}

}