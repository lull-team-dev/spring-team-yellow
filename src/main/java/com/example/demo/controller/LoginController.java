package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Guest;
import com.example.demo.model.Account;
import com.example.demo.repository.GuestRepository;

@Controller
public class LoginController {

	@Autowired
	HttpSession session;
	@Autowired
	Account account;
	@Autowired
	GuestRepository guestRepository;

	//ログイン画面の表示
	@GetMapping({ "/", "/login", "/logout" })
	private String loginIndex() {
		session.invalidate();
		return "login";
	}

	//ログイン処理
	@PostMapping("/login")
	public String login(@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {

		//バリデーション処理
		List<String> errorList = new ArrayList<>();
		if (password.isEmpty()) {
			errorList.add("パスワードは必須です");
		}
		if (email.isEmpty()) {
			errorList.add("メールアドレスは必須です");
		}

		List<Guest> guests = guestRepository.findByEmailAndPassword(email, password);
		if ((!email.isEmpty() && !password.isEmpty()) && guests.size() <= 0) {
			errorList.add("メールアドレスまたはパスワードが違います");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "login";
		}

		Guest guest = guests.get(0);
		account.setId(guest.getId());
		account.setName(guest.getName());

		return "redirect:/room";
	}

}
