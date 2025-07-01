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
import com.example.demo.entity.Plan;
import com.example.demo.entity.Room;
import com.example.demo.model.Account;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RoomRepository;

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

	@GetMapping("/rooms/{id}/reserve")
	public String reserveIndex(
			@PathVariable("id") Integer roomId,
			@RequestParam(required = false) Integer planId,
			@RequestParam(required = false) LocalDate checkinDate,
			@RequestParam(required = false) LocalDate checkoutDate,
			Model model) {

		//宿泊予定者情報の取得
		Guest guest = guestRepository.findById(account.getId()).get();
		//部屋の情報取得
		Room room = roomRepository.findById(roomId).get();
		//画像をリストにする
		List<String> imgList = List.of(room.getImgPath(), room.getImgPath2());
		//プラン情報取得
		Plan plan = planRepository.findById(planId).get();

		model.addAttribute("guest", guest);
		model.addAttribute("room", room);
		model.addAttribute("plan", plan);
		model.addAttribute("imgList", imgList);
		model.addAttribute("checkinDate", checkinDate);
		model.addAttribute("checkoutDate", checkoutDate);

		return "reserve";
	}

}
