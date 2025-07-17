// ログアウト状態でもOK
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
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
	GuestRepository guestRepository;
	@Autowired
	HttpSession session;
	@Autowired
	Account account;

	//ログイン画面の表示
	@GetMapping("/login")
	private String loginIndex(
			@RequestParam(defaultValue = "") String error,
			HttpServletRequest request,
			Account account,
			Model model) {

		//ログイン前にいたURLの取得
		String referer = request.getHeader("Referer");
		System.out.println("ログイン画面if前" + referer);

		if (referer != null && (!referer.contains("/login") || !referer.contains("/register"))) {
			System.out.println("ログイン画面遷移①" + referer);
			account.setRefererUrl(referer);
			System.out.println("ログイン画面遷移②" + account.getRefererUrl());
		}

		// クエリパラメータで"notLoggedIn"を受け取った場合
		if (error.equals("notLoggedIn")) {
			model.addAttribute("error", "ログインしてください");
		}

		return "login";
	}

	@GetMapping("/logout")
	private String logoutIndex(
			@RequestParam(defaultValue = "") String error,
			HttpSession session,
			Model model) {

		// セッションが存在する場合のみ破棄する
		session.invalidate();

		// クエリパラメータで"notLoggedIn"を受け取った場合
		if (error.equals("notLoggedIn")) {
			model.addAttribute("error", "ログインしてください");
		}

		return "login";
	}

	//ログイン処理
	@PostMapping("/login")
	public String login(@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {

		//バリデーション処理
		boolean hasError = false;

		List<String> errorList = new ArrayList<>();
		if (password.isEmpty()) {
			model.addAttribute("passwordError", "パスワードは必須です");
			hasError = true;
		}
		if (email.isEmpty()) {
			model.addAttribute("emailError", "メールアドレスは必須です");
			hasError = true;
		}
		List<Guest> guests = guestRepository.findByEmailAndPassword(email, password);
		if ((!email.isEmpty() && !password.isEmpty()) && guests.size() <= 0) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが違います");
			hasError = true;
		}
		//		if (errorList.size() > 0) {
		//			model.addAttribute("errorList", errorList);
		//			return "login";
		//		}
		if (hasError) {
			return "login";
		}

		Guest guest = guests.get(0);
		account.setId(guest.getId());
		account.setName(guest.getName());

		//セッションに保管されたURLのチェック
		if (account.getRefererUrl() != null) {
			System.out.println("ログインする直前" + account.getRefererUrl());
			return "redirect:" + account.getRefererUrl();
		}

		return "redirect:/room";
	}

}
