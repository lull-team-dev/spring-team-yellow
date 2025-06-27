package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Plan;
import com.example.demo.entity.Room;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RoomRepository;

@Controller
public class EditController {

	@Autowired
	RoomRepository roomRepository;
	@Autowired
	PlanRepository planRepository;

	@GetMapping("/rooms/{id}")
	public String showRoomDetails(@PathVariable("id") Integer id,
			Model model) {

		//部屋の情報取得
		Room room = roomRepository.findById(id).get();
		//プラン情報取得
		List<Plan> plans = planRepository.findAll();
		
		model.addAttribute("room", room);
		model.addAttribute("plans", plans);


		return "edit";
	}

}
