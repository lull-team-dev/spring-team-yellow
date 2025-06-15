package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;

@Controller
public class RoomController {

	@Autowired
	RoomRepository roomRepository;

	@GetMapping("/room")
	public String showRoomList(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "0") Integer underPrice,
			@RequestParam(required = false, defaultValue = "100000") Integer upPrice,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			Model model) {

		List<Room> rooms = null;

		rooms = roomRepository.findAll();

		model.addAttribute("rooms", rooms);
		model.addAttribute("keyword", keyword);
		model.addAttribute("price", underPrice);
		model.addAttribute("price", upPrice);
		model.addAttribute("checkinDate", checkinDate);
		model.addAttribute("checkoutDate", checkoutDate);
		return "top";
	}
}
