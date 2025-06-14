package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class roomController {

	@GetMapping("/room")
	public String showRoomList() {
		return "top";
	}
}
