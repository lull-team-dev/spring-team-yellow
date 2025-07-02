package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Like;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Room;
import com.example.demo.entity.Type;
import com.example.demo.model.Account;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.ReservDataRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TypeRepository;
import com.example.demo.service.ChangeCharService;
import com.example.demo.service.LikeSeravice;
import com.example.demo.service.RoomService;

@Controller
public class RoomController {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	TypeRepository typeRepository;

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservDataRepository reservDataRepository;

	@Autowired
	RoomService roomService;

	@Autowired
	ChangeCharService changeCharService;

	@Autowired
	LikeSeravice likeSeravice;

	@Autowired
	Account account;

	@GetMapping("/room")
	public String showRoomList(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) List<Integer> types,
			@RequestParam(required = false, defaultValue = "0") Integer underPrice,
			@RequestParam(required = false, defaultValue = "100000") Integer upPrice,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			Model model) {

		List<Room> rooms = null;
		List<LocalDate> stayDates = null;
		List<Integer> roomIds = new ArrayList<>();

		//ひらがなをカタカナに変換
		String kanaWord = null;
		if (keyword != null) {
			kanaWord = changeCharService.convertHiraganaToKatakana(keyword);
		}

		//選択範囲の日付をリストにひとつずつ格納
		if (checkinDate != null && checkoutDate != null) {
			stayDates = roomService.dateCalc(checkinDate, checkoutDate);
			List<Reservation> resevations = reservationRepository.findByReservDatasStayOneDateIn(stayDates);

			//予約が入っていた場合（Not nullへ対応するため）
			if (resevations.size() != 0) {
				for (Reservation reserve : resevations) {
					roomIds.add(reserve.getRoom().getId());
				}

				//100,000以上の場合の検索
				if (upPrice == 100000) {

					//ルーム名　+　タイプの検索 + 予約あり（金額は最低金額以上）
					if (keyword != null && types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndTypeInAndPriceGreaterThanEqual(
								roomIds,
								kanaWord,
								typeList,
								underPrice);
					} else if (keyword != null) {
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndPriceGreaterThanEqual(
								roomIds,
								kanaWord,
								underPrice);
					} else if (types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndTypeInAndPriceGreaterThanEqual(
								roomIds,
								typeList,
								underPrice);
					} else {
						rooms = roomRepository.findByIdNotInAndPriceGreaterThanEqual(roomIds, underPrice);
					}
				} else {
					//ルーム名　+　タイプの検索 + 予約あり（金額は最低と最高の間）
					if (keyword != null && types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndTypeInAndPriceBetween(
								roomIds,
								kanaWord,
								typeList,
								underPrice,
								upPrice);
					} else if (keyword != null) {
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndPriceBetween(
								roomIds,
								kanaWord,
								underPrice,
								upPrice);
					} else if (types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndTypeInAndPriceBetween(
								roomIds,
								typeList,
								underPrice,
								upPrice);
					} else {
						rooms = roomRepository.findByIdNotInAndPriceBetween(roomIds, underPrice, upPrice);
					}
				}
			} else {
				//100,000未満
				if (upPrice == 100000) {
					//ルーム名　+　タイプの検索 + 予約なし（金額は最低金額以上）
					if (keyword != null && types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByRoomNameContainingAndTypeInAndPriceGreaterThanEqual(
								kanaWord,
								typeList,
								underPrice);
					} else if (keyword != null) {
						rooms = roomRepository.findByRoomNameContainingAndPriceGreaterThanEqual(
								kanaWord,
								underPrice);
					} else if (types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByTypeInAndPriceGreaterThanEqual(
								typeList,
								underPrice);
					} else {
						rooms = roomRepository.findByPriceGreaterThanEqual(underPrice);
					}
				} else {
					//ルーム名　+　タイプの検索 + 予約なし（金額は最低と最高の間）
					if (keyword != null && types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByRoomNameContainingAndTypeInAndPriceBetween(
								kanaWord,
								typeList, underPrice,
								upPrice);
					} else if (keyword != null) {
						rooms = roomRepository.findByRoomNameContainingAndPriceBetween(
								kanaWord,
								underPrice,
								upPrice);
					} else if (types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByTypeInAndPriceBetween(
								typeList,
								underPrice,
								upPrice);
					} else {
						rooms = roomRepository.findByPriceBetween(underPrice, upPrice);
					}
				}
			}
		} else {
			//検索内容がない場合全件表示
			rooms = roomRepository.findAll();
		}

		if (rooms.size() == 0) {
			model.addAttribute("message", "検索がヒットしません");
		}

		List<Like> likes = likeRepository.findByGuestId(account.getId());
		List<Integer> likeRoom = new ArrayList<>();
		for (Like like : likes) {
			likeRoom.add(like.getRoomId());
		}

		model.addAttribute("stayDates", stayDates);
		model.addAttribute("rooms", rooms);
		model.addAttribute("keyword", keyword);
		model.addAttribute("types", types);
		model.addAttribute("underPrice", underPrice);
		model.addAttribute("upPrice", upPrice);
		model.addAttribute("checkinDate", checkinDate);
		model.addAttribute("checkoutDate", checkoutDate);
		model.addAttribute("like", likeRoom);

		return "top";
	}

	@GetMapping("/rooms/{id}/like")
	public String like(@PathVariable("id") Integer id,
			Model model) {

		likeSeravice.toggleLike(account.getId(), id);

		return "redirect:/room";

	}
}
