// ログアウト状態でもOK
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Account;

@Controller
public class HotelTopController {

	@Autowired
	Account account;

	@GetMapping("/hotel")
	public String showHotel(Model model) {
		List<String> imgList = new ArrayList<>();
		imgList.add("/uploads/images/hotel.png");
		imgList.add("/uploads/images/hotel-out1.png");
		imgList.add("/uploads/images/hotel-out2.png");
		imgList.add("/uploads/images/hotel-out3.png");
		model.addAttribute("imgList", imgList);
		return "hotel";
	}

	@GetMapping("/privacy")
	public String privacy() {
		return "privacy";
	}

	@GetMapping("/terms")
	public String terms() {
		return "terms";
	}
}
