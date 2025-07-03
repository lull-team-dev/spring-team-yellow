package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Plan;
import com.example.demo.entity.Room;
import com.example.demo.model.Account;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.LikeService;

@Controller
public class DetailController {

	@Autowired
	RoomRepository roomRepository;
	@Autowired
	PlanRepository planRepository;
	@Autowired
	LikeRepository likeRepository;
	@Autowired
	LikeService likeService;
	@Autowired
	Account account;

	@GetMapping("/rooms/{id}")
	public String showRoomDetails(@PathVariable("id") Integer id,
			@RequestParam(name = "checkinDate", required = false) String checkinDate,
			@RequestParam(name = "checkoutDate", required = false) String checkoutDate,

			Model model) {

		//部屋の情報取得
		Room room = roomRepository.findById(id).get();
		//画像をリストにする
		List<String> imgList = List.of(room.getImgPath(), room.getImgPath2());
		//プラン情報取得
		List<Plan> plans = planRepository.findAll();

		//		いいね一覧取得
		List<Integer> likeRoom = likeService.likeIcon();
		model.addAttribute("like", likeRoom);

		model.addAttribute("room", room);
		model.addAttribute("plans", plans);
		model.addAttribute("imgList", imgList);
		model.addAttribute("checkinDate", checkinDate);
		model.addAttribute("checkoutDate", checkoutDate);

		return "detail";
	}

	//	お気に入り処理
	@GetMapping("/detail/{id}/like")
	public String like(@PathVariable("id") Integer id,
			Model model) {

		likeService.toggleLike(account.getId(), id);

		return "redirect:/rooms/{id}#" + id;
	}

}
