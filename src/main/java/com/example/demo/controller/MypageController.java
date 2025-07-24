// ログインのみ
package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Guest;
import com.example.demo.model.Account;
import com.example.demo.repository.GuestRepository;

@Controller
public class MypageController {
	@Autowired
	Account account;
	@Autowired
	GuestRepository guestRepository;

	@GetMapping("/mypage")
	public String profile() {
		return "mypage";
	}

	@GetMapping("/profile")
	public String guest(Model model) {
		//ログインユーザーの情報を取得
		Guest guest = guestRepository.findById(account.getId()).get();
		model.addAttribute("guest", guest);
		return "profile";
	}

	@GetMapping("/mypage/{edit}")
	public String edit(@PathVariable("edit") String edit,
			Model model) {
		Guest guest = guestRepository.findById(account.getId()).get();
		model.addAttribute("guest", guest);
		model.addAttribute("name", guest.getName());
		model.addAttribute("address", guest.getAddress());
		model.addAttribute("tel", guest.getTel());

		model.addAttribute("edit", edit);

		return "edit";
	}

	@PostMapping("/mypage/{edit}")
	public String updata(
			@PathVariable("edit") String edit,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "newPassword", required = false) String newPassword,
			@RequestParam(name = "password_confirm", required = false) String password_confirm,
			Model model) {

		Guest guest = guestRepository.findById(account.getId()).get();
		Map<String, String> errorMap = new HashMap<>();

		switch (edit) {
		case "email":

			//登録があるメールアドレスか
			Optional<Guest> emails = guestRepository.findByEmail(email);

			if (email == null || email.isEmpty()) {
				errorMap.put("email", "メールアドレスの入力は必須です");
			} else if (guestRepository.existsByIdAndEmail(account.getId(), email)) {
				errorMap.put("email", "現在登録中のメールアドレスです");
			} else if (!emails.isEmpty()) {
				errorMap.put("email", "このメールアドレスは利用できません");
			} else {
				guest.setEmail(email);
			}
			if (!errorMap.isEmpty()) {
				model.addAttribute("guest", guest);
				model.addAttribute("errors", errorMap);
				model.addAttribute("edit", edit);
				model.addAttribute("email", email);

				return "edit";
			}
			break;

		case "password":
			if ((password == null && newPassword == null) || (password.isEmpty() && newPassword.isEmpty())) {
				errorMap.put("password", "現在のパスワードの入力は必須です");
				errorMap.put("newPassword", "新しいパスワードの入力は必須です");
			} else if (password == null || password.isEmpty()) {
				errorMap.put("password", "現在のパスワードの入力は必須です");
			} else if (password != null && !guestRepository.existsByIdAndPassword(account.getId(), password)) {
				errorMap.put("password", "現在のパスワードが間違っています");
			} else if (newPassword == null || newPassword.isEmpty()) {
				errorMap.put("newPassword", "新しいパスワードの入力は必須です");
			} else if (password_confirm == null || password_confirm.isEmpty()) {
				errorMap.put("password_confirm", "パスワード(確認)の入力は必須です");
			} else if (newPassword == null || newPassword.length() < 5 || newPassword.length() > 100) {
				errorMap.put("newPassword", "パスワードは5〜100文字");
			} else if (newPassword != null && !newPassword.equals(password_confirm)) {
				errorMap.put("password_confirm", "パスワードが一致しません");
			}
			if (!errorMap.isEmpty()) {
				model.addAttribute("guest", guest);
				model.addAttribute("errors", errorMap);
				model.addAttribute("edit", edit);
				return "edit";
			} else {
				guest.setPassword(newPassword);
			}
			break;

		case "profile":
			//エラーチェック
			if (name.length() > 15) {
				errorMap.put("name", "名前は15文字以内で入力してください");
			} else if (name == null || name.isEmpty()) {
				errorMap.put("name", "名前の入力は必須です");
			}
			if (address.length() > 120) {
				errorMap.put("address", "住所は120文字以内で入力してください");
			} else if (address == null || address.isEmpty()) {
				errorMap.put("address", "住所の入力は必須です");
			}
			if (!tel.matches("^[0-9]{10,15}$")) {
				errorMap.put("tel", "電話番号は10文字以上15文字以内の数字で入力してください");
			} else if (tel == null || tel.isEmpty()) {
				errorMap.put("tel", "電話番号の入力は必須です");
			}

			if (!errorMap.isEmpty()) {
				model.addAttribute("guest", guest);
				model.addAttribute("errors", errorMap);
				model.addAttribute("edit", edit);
				model.addAttribute("name", name);
				model.addAttribute("address", address);
				model.addAttribute("tel", tel);

				return "edit";
			}
			//セット
			if (name != null || !name.isEmpty()) {
				guest.setName(name);
				account.setName(name);
			}
			if (address != null || !address.isEmpty()) {
				guest.setAddress(address);
			}
			if (tel != null || !tel.isEmpty()) {
				guest.setTel(tel);
			}
			break;
		}

		guestRepository.save(guest);

		return "redirect:/profile";
	}

}
