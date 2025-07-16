package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Room;
import com.example.demo.model.Account;
import com.example.demo.repository.LikeRepository;
import com.example.demo.service.LikeService;
import com.example.demo.service.RandomImg;

@Controller
public class RoomLikeController {

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	LikeService likeService;

	@Autowired
	Account account;

	@Autowired
	RandomImg randomImg;

	//	お気に入り処理
	@GetMapping("/like/{id}")
	public String like(@PathVariable("id") Integer id,
			HttpServletRequest request,
			Model model) {

		likeService.toggleLike(account.getId(), id);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer + '#' + id;
	}

	// 過去のやつ
	@GetMapping("/rooms/{id}/like")
	public String roomsLike(@PathVariable("id") Integer id,
			HttpServletRequest request,
			Model model) {

		likeService.toggleLike(account.getId(), id);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer + '#' + id;
	}

	// 過去のやつ
	@GetMapping("/detail/{id}/like")
	public String detailLike(@PathVariable("id") Integer id,
			HttpServletRequest request,
			Model model) {

		likeService.toggleLike(account.getId(), id);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer + '#' + id;
	}

	@GetMapping("/like")
	public String showLikeRoom(Model model) {
		//		いいねした部屋一覧
		List<Room> rooms = likeService.getLikedRooms(account.getId());
		model.addAttribute("rooms", rooms);

		//		いいね一覧取得
		List<Integer> likeRoom = likeService.likeIcon();
		model.addAttribute("like", likeRoom);
		model.addAttribute("randomImg", randomImg.showImg());

		return "like";
	}

	//	お気に入り処理
	@GetMapping("/like/{id}/like")
	public String like(@PathVariable("id") Integer id,
			Model model) {

		likeService.toggleLike(account.getId(), id);

		return "redirect:/like#" + id;
	}
}
