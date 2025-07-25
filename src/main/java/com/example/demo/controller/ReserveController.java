// ログインのみ
package com.example.demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.reserveDto;
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
import com.example.demo.service.ReserveCodeMail;
import com.example.demo.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j //コンソールログ用
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
	RoomService roomService;
	@Autowired
	ReserveCodeMail reserveCodeMail;

	//予約ホーム画面
	@GetMapping("/rooms/{id}/reserve")
	public String reserveIndex(
			@PathVariable("id") Integer roomId,
			@RequestParam(required = false) Integer planId,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			@RequestParam(required = false) Integer guestCount,
			Model model,
			@ModelAttribute("guestName") String guestName,
			@ModelAttribute("email") String email,
			@ModelAttribute("tel") String tel,
			@ModelAttribute("address") String address,
			@ModelAttribute("reserveError") String reserveErorr) {

		//宿泊予定者情報の取得
		Guest guest = guestRepository.findById(account.getId()).get();
		//部屋の情報取得
		Room room = roomRepository.findById(roomId).get();
		//画像をリストにする
		List<String> imgList = List.of(room.getImgPath(), room.getImgPath2());
		//プラン情報取得
		Plan plan = planRepository.findById(planId).get();

		//部屋タイプに応じた宿泊者数の制限
		//部屋タイプに応じた宿泊者数の制限
		List<Integer> maxGuestCount = new ArrayList<>();
		int max = 1; // シングル
		if (room.getType().getId() == 2) {
			max = 2; // ダブル
		} else if (room.getType().getId() == 3) {
			max = 4; // スイート
		}

		for (int i = max; i >= 1; i--) {
			maxGuestCount.add(i);
		}

		model.addAttribute("room", room);
		model.addAttribute("plan", plan);
		model.addAttribute("imgList", imgList);
		model.addAttribute("maxGuestCount", maxGuestCount);
		model.addAttribute("reserveError", reserveErorr);

		reserveDto dto = new reserveDto();

		//リダイレクトの場合内容を送られてきた情報をそのままセット
		if (guestName != null && !guestName.isBlank()) {
			dto.setGuestName(guestName);
		} else {
			dto.setGuestName(guest.getName());
		}

		if (email != null && !email.isBlank()) {
			dto.setEmail(email);
		} else {
			dto.setEmail(guest.getEmail());
		}

		if (tel != null && !tel.isBlank()) {
			dto.setTel(tel);
		} else {
			dto.setTel(guest.getTel());
		}

		if (address != null && !address.isBlank()) {
			dto.setAddress(address);
		} else {
			dto.setAddress(guest.getAddress());
		}

		dto.setGuestCount(guestCount);
		dto.setCheckinDate(checkinDate);
		dto.setCheckoutDate(checkoutDate);

		model.addAttribute("reserveDto", dto);

		return "reserve";
	}

	//予約確認画面
	@PostMapping("/rooms/{id}/reserve")
	public String reserve(
			@PathVariable("id") Integer roomId,
			@RequestParam(required = false) Integer planId,
			@ModelAttribute("reserveDto") @Valid reserveDto form,
			BindingResult bindingResult,
			Model model) {

		//部屋の情報取得
		Room room = roomRepository.findById(roomId).get();
		//画像をリストにする
		List<String> imgList = List.of(room.getImgPath(), room.getImgPath2());
		//プラン情報取得
		Plan plan = planRepository.findById(planId).get();

		//部屋タイプに応じた宿泊者数の制限
		List<Integer> maxGuestCount = new ArrayList<>();
		int max = 1; // シングル
		if (room.getType().getId() == 2) {
			max = 2; // ダブル
		} else if (room.getType().getId() == 3) {
			max = 4; // スイート
		}
		for (int i = 1; i <= max; i++) {
			maxGuestCount.add(i);
		}

		model.addAttribute("room", room);
		model.addAttribute("plan", plan);
		model.addAttribute("imgList", imgList);
		model.addAttribute("maxGuestCount", maxGuestCount);

		//通常バリデーション
		if (bindingResult.hasErrors()) {
			return "reserve";
		}

		//メールアドレスチェック
		boolean uniqueEmail = guestRepository.existsByIdNotAndEmail(account.getId(), form.getEmail());
		if (uniqueEmail) {
			bindingResult.rejectValue("email", "uniqueEmail", "このメールアドレスは使用できません");
			return "reserve"; // エラー時は入力画面に戻す
		}

		//予約詳細の日付細分化
		List<LocalDate> stayDates = roomService.dateCalc(form.getCheckinDate(), form.getCheckoutDate());

		// 空き部屋チェック
		if (roomService.isRoomAvailable(roomId, stayDates)) {
			bindingResult.rejectValue("checkinDate", "room.notAvailable", "選択した日付は既に予約されています");
			return "reserve"; // エラー時は入力画面に戻す
		}

		// 宿泊者の重複予約チェック
		if (roomService.hasGuestReservedOnDates(stayDates)) {
			bindingResult.rejectValue("checkinDate", "guest.duplicateReservation", "ご自身ですでにこの日程で予約があります");
			return "reserve";
		}

		//１日あたりのプラン料金+ルーム料金+宿泊者人数
		Integer dayTotalPrice = plan.calcTotalPrice(room.getPrice()) * form.getGuestCount();
		//（１日あたりのプラン料金+ルーム料金+宿泊者人数）×宿泊日
		Integer countDay = (int) ChronoUnit.DAYS.between(form.getCheckinDate(), form.getCheckoutDate());

		Integer TotalPrice = dayTotalPrice * countDay;

		//問題ないため、全てのデータをバリューに送る。
		model.addAttribute("TotalPrice", TotalPrice);
		model.addAttribute("countDay", countDay);
		model.addAttribute("guestName", form.getGuestName());
		model.addAttribute("guestCount", form.getGuestCount());
		model.addAttribute("email", form.getEmail());
		model.addAttribute("tel", form.getTel());
		model.addAttribute("address", form.getAddress());
		model.addAttribute("checkinDate", form.getCheckinDate());
		model.addAttribute("checkoutDate", form.getCheckoutDate());
		return "reserveConf";
	}

	//予約ロジック
	@Transactional
	@PostMapping("/rooms/{id}/reserveConf")
	public String reserveConf(
			@PathVariable("id") Integer roomId,
			@RequestParam(required = false) String guestName,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String tel,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) Integer planId,
			@RequestParam(required = false) Integer guestCount,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			@RequestParam(required = false) Integer status,
			RedirectAttributes redirectAttributes) {

		if (status == 2) {
			redirectAttributes.addAttribute("id", roomId);
			redirectAttributes.addAttribute("planId", planId);
			redirectAttributes.addAttribute("checkinDate", checkinDate);
			redirectAttributes.addAttribute("checkoutDate", checkoutDate);
			redirectAttributes.addAttribute("guestCount", guestCount);
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
		account.setName(guestName);

		//（プラン料金＋ルーム料金）×宿泊日
		Integer countDay = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);
		Integer dayTotalPrice = plan.calcTotalPrice(room.getPrice());
		Integer totalPrice = guestCount * (dayTotalPrice * countDay);

		//予約の作成
		Reservation reserve = new Reservation(guest, room, plan, guestCount, totalPrice, countDay, checkinDate);

		//予約詳細の日付細分化
		List<LocalDate> stayDates = roomService.dateCalc(checkinDate, checkoutDate);

		List<ReservData> reservDatas = reserve.getReservDatas();

		for (LocalDate stayDate : stayDates) {
			ReservData reserveData = new ReservData(stayDate, dayTotalPrice, reserve);
			reservDatas.add(reserveData);
		}

		try {
			//予約保存
			reserveRepository.save(reserve);
			//保存完了後メール送信
			reserveCodeMail.mailSend(reserve);

		} catch (Exception e) {
			//			log.error("予約処理に失敗しました: {}", e.getMessage());
			redirectAttributes.addAttribute("planId", planId);
			redirectAttributes.addAttribute("checkinDate", checkinDate);
			redirectAttributes.addAttribute("checkoutDate", checkoutDate);
			redirectAttributes.addAttribute("guestCount", guestCount);
			redirectAttributes.addFlashAttribute("guestName", guestName);
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("tel", tel);
			redirectAttributes.addFlashAttribute("address", address);
			redirectAttributes.addFlashAttribute("reserveError", "予約に失敗しました。<br>時間をおいて再度お試しください。");

			throw new RuntimeException("メール送信に失敗しました", e);
		}

		redirectAttributes.addAttribute("id", reserve.getId());
		redirectAttributes.addAttribute("afterReserve", true);

		return "redirect:/reservationHistory/{id}";
	}

}