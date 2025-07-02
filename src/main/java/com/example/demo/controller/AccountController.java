package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String accountIndex(Model model) {
		model.addAttribute("guest", new Guest());
		return "register";
	}

	// 新規会員登録処理
	@PostMapping("/register")
	public String addAccount(
			@Valid @ModelAttribute Guest guest,
			BindingResult bindingResult,
			@RequestParam String password_confirm,
			RedirectAttributes redirectAttributes,
			Model model) {

		// バリデーションにエラーがある場合
		if (bindingResult.hasErrors()) {
			return "register";
		}

		// メールアドレスの重複チェック
		if (guestRepository.findByEmail(guest.getEmail()).isPresent()) {
			model.addAttribute("error", "このメールアドレスはすでに登録されています");
			model.addAttribute("guest", guest);
			return "register";
		}

		// パスワードの一致チェック
		if (!guest.getPassword().equals(password_confirm)) {
			model.addAttribute("error", "パスワードが一致しません");
			model.addAttribute("guest", guest);
			return "register";
		}

		guestRepository.save(guest);
		redirectAttributes.addFlashAttribute("email", guest.getEmail());

		return "redirect:/login";
	}

}
