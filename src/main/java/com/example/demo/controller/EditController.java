package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditController {

	@GetMapping("/rooms/2")
	public String showRoomDetails() {
		return "edit";
	}

}
