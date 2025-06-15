package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Room;
import com.example.demo.entity.Type;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TypeRepository;

@Controller
public class RoomController {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	TypeRepository typeRepository;

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
		//		if (checkinDate != null && checkoutDate != null) {
		//
		//		}
		if (keyword != null && types != null) {
			List<Type> typeList = typeRepository.findByIdIn(types);
			rooms = roomRepository.findByRoomNameContainingAndTypeInAndPriceBetween(keyword, typeList, underPrice,
					upPrice);
		} else if (keyword != null) {
			rooms = roomRepository.findByRoomNameContainingAndPriceBetween(keyword, underPrice, upPrice);
		} else if (types != null) {
			List<Type> typeList = typeRepository.findByIdIn(types);
			rooms = roomRepository.findByTypeInAndPriceBetween(typeList, underPrice, upPrice);
		} else {
			rooms = roomRepository.findAll();
		}

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
