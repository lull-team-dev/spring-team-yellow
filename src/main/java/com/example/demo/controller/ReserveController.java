package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReserveController {

	//予約フォームの表示 
	@GetMapping("/rooms/{id}/reserve")
	public String reserveIndex(
			@PathVariable("id") Long id,
			@RequestParam(required = false) String error,
			Model model) {

		model.addAttribute("roomId", id);
		model.addAttribute("error", error);
		return "reserve";
	}

}
