package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;

@Controller
public class EditController {

	@Autowired
	RoomRepository roomRepository;

	@GetMapping("/rooms/{id}")
	public String showRoomDetails(@PathVariable("id") Integer id,
			Model model) {

		//部屋の情報取得
		Room room = roomRepository.findById(id).get();
		model.addAttribute("room", room);

		return "edit";
	}

}
