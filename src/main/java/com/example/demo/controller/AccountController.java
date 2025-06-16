package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.GuestRepository;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	GuestRepository guestRepository;

	// 新規会員登録画面の表示
	@GetMapping("/register")
	public String accountIndex() {
		return "register";
	}

}
