package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;

@Controller

public class ReserveController {

	@Autowired
	private RoomRepository roomRepository;

	//予約フォームの表示 
	@GetMapping("/rooms/{id}/reserve")
	public String reserveIndex(
			@PathVariable("id") Integer id,
			Model model) {
		Room room = roomRepository.findById(id).get();

		model.addAttribute("room", room);
		return "reserve";
	}

}
