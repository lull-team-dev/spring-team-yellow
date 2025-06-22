package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.Room;
import com.example.demo.entity.Type;
import com.example.demo.repository.ReservDataRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TypeRepository;
import com.example.demo.service.RoomService;

@Controller
public class RoomController {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	TypeRepository typeRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservDataRepository reservDataRepository;

	@Autowired
	RoomService roomService;

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

		if (checkinDate != null && checkoutDate != null) {
			stayDates = roomService.dateCalc(checkinDate, checkoutDate);
			List<Reservation> resevations = reservationRepository.findByReservDataStayOneDateIn(stayDates);
			List<Room> roomAll = roomRepository.findAll();

			for (Room room : roomAll) {
				if (resevations != null) {
					for (Reservation reserve : resevations) {
						if (room.getId() == reserve.getRoom().getId()) {
							roomIds.add(room.getId());
						}
					}
				} else {
					roomIds.add(room.getId());
				}

				if (upPrice == 100000) {
					if (keyword != null && types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndTypeInAndPriceGreaterThanEqual(
								roomIds,
								keyword,
								typeList,
								underPrice);
					} else if (keyword != null) {
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndPriceGreaterThanEqual(roomIds,
								keyword,
								underPrice);
					} else if (types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndTypeInAndPriceGreaterThanEqual(roomIds, typeList,
								underPrice);
					} else {
						rooms = roomRepository.findByIdNotInAndPriceGreaterThanEqual(roomIds, underPrice);
					}
				} else {
					if (keyword != null && types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndTypeInAndPriceBetween(roomIds,
								keyword,
								typeList, underPrice,
								upPrice);
					} else if (keyword != null) {
						rooms = roomRepository.findByIdNotInAndRoomNameContainingAndPriceBetween(roomIds, keyword,
								underPrice,
								upPrice);
					} else if (types != null) {
						List<Type> typeList = typeRepository.findByIdIn(types);
						rooms = roomRepository.findByIdNotInAndTypeInAndPriceBetween(roomIds, typeList, underPrice,
								upPrice);
					} else {
						rooms = roomRepository.findByIdNotInAndPriceBetween(roomIds, underPrice, upPrice);
					}
				}
			}
		} else {
			rooms = roomRepository.findAll();
		}

		model.addAttribute("stayDates", stayDates);
		model.addAttribute("rooms", rooms);
		model.addAttribute("keyword", keyword);
		model.addAttribute("types", types);
		model.addAttribute("underPrice", underPrice);
		model.addAttribute("upPrice", upPrice);
		model.addAttribute("checkinDate", checkinDate);
		model.addAttribute("checkoutDate", checkoutDate);

		return "top";
	}
}
