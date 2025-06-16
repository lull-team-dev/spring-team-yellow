package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Guest;
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

	// 新規会員登録処理
	@PostMapping("/register")
	public String addAccount(@ModelAttribute Guest guest,
			@RequestParam String email,
			@RequestParam String password_confirm,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (!guest.getPassword().equals(password_confirm)) {
			model.addAttribute("error", "パスワードが一致しません");
			return "register";
		}

		guestRepository.save(guest);
		redirectAttributes.addFlashAttribute("email", email);

		return "redirect:/login";
	}

}
