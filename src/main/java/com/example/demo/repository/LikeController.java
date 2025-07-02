package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Room;
import com.example.demo.model.Account;
import com.example.demo.service.LikeService;

@Controller
public class LikeController {

	@Autowired
	LikeRepository likeRepository;
	@Autowired
	LikeService likeService;
	@Autowired
	Account account;

	@GetMapping("/like")
	public String showLikeRoom(Model model) {
		//		いいねした部屋一覧
		List<Room> rooms = likeService.getLikedRooms(account.getId());
		model.addAttribute("rooms", rooms);

		//		いいね一覧取得
		List<Integer> likeRoom = likeService.likeIcon();
		model.addAttribute("like", likeRoom);

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
