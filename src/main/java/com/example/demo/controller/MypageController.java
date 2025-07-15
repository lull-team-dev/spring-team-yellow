// ログインのみ
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
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
	public String guest(Model model) {
		//ログインユーザーの情報を取得
		Guest guest = guestRepository.findById(account.getId()).get();
		model.addAttribute("guest", guest);
		return "mypage";
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
		List<String> errorList = new ArrayList<>();
		switch (edit) {
		case "email":

			//登録があるメールアドレスか
			Optional<Guest> emails = guestRepository.findByEmail(email);

			if (email == null || email.isEmpty()) {
				errorList.add("メールアドレスの入力は必須です");
			} else if (!emails.isEmpty()) {
				errorList.add("すでに登録のあるメールアドレスです");
			} else {
				guest.setEmail(email);
			}
			if (errorList.size() > 0) {
				model.addAttribute("guest", guest);
				model.addAttribute("errorList", errorList);
				model.addAttribute("edit", edit);
				model.addAttribute("email", email);

				return "edit";
			}
			break;

		case "password":
			if ((password == null && newPassword == null) || (password.isEmpty() && newPassword.isEmpty())) {
				errorList.add("現在のパスワードの入力は必須です");
				errorList.add("新しいパスワードの入力は必須です");
			} else if (password == null || password.isEmpty()) {
				errorList.add("現在のパスワードの入力は必須です");
			} else if (password != null && !guestRepository.existsByIdAndPassword(account.getId(), password)) {
				errorList.add("現在のパスワードが間違っています");
			} else if (newPassword == null || newPassword.isEmpty()) {
				errorList.add("新しいパスワードの入力は必須です");
			} else if (password_confirm == null || password_confirm.isEmpty()) {
				errorList.add("パスワード(確認)の入力は必須です");
			} else if (password == null || password.length() < 5 || password.length() > 100) {
				errorList.add("パスワードは5〜100文字");
			} else if (password != null || !password.equals(password_confirm)) {
				errorList.add("パスワードが一致しません");
			}
			if (errorList.size() > 0) {
				model.addAttribute("guest", guest);
				model.addAttribute("errorList", errorList);
				model.addAttribute("edit", edit);
				return "edit";
			} else {
				guest.setPassword(newPassword);
			}
			break;

		case "profile":
			//			エラーチェック
			if (name.length() > 15) {
				errorList.add("名前は15文字以内で入力してください");
			} else if (name == null || name.isEmpty()) {
				errorList.add("名前の入力は必須です");
			}
			if (address.length() > 120) {
				errorList.add("住所は120文字以内で入力してください");
			} else if (address == null || address.isEmpty()) {
				errorList.add("住所の入力は必須です");
			}
			if (tel.length() > 15) {
				errorList.add("電話番号は15文字以内で入力してください");
			} else if (tel == null || tel.isEmpty()) {
				errorList.add("電話番号の入力は必須です");
			}

			if (errorList.size() > 0) {
				model.addAttribute("guest", guest);
				model.addAttribute("errorList", errorList);
				model.addAttribute("edit", edit);
				model.addAttribute("name", name);
				model.addAttribute("address", address);
				model.addAttribute("tel", tel);

				return "edit";
			}
			//			セット
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

		return "redirect:/mypage";
	}

}
