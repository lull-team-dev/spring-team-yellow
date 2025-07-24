package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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

	// いいね処理（非同期）
	@GetMapping("/like/{id}")
	@ResponseBody // ←追加すると明示的にJSONレスポンスにも対応

	public String like(@PathVariable("id") Integer id,
			HttpServletRequest request,
			Model model) {

		boolean liked = likeService.toggleLike(account.getId(), id);

		return liked ? "liked" : "unliked";
	}

	//いいね一覧表示
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

}
