package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Like;
import com.example.demo.model.Account;
import com.example.demo.repository.LikeRepository;

@Service
public class LikeService {
	@Autowired
	Account account;

	@Autowired
	private LikeRepository likeRepository;

	public List<Integer> likeIcon() {
		List<Like> likes = likeRepository.findByGuestId(account.getId());
		List<Integer> likeRoom = new ArrayList<>();
		for (Like like : likes) {
			likeRoom.add(like.getRoomId());
		}
		return likeRoom;
	}

	//	いいね押した時の処理
	public void toggleLike(Integer guestId, Integer roomId) {
		//		いいねされてたら、テーブルから削除
		if (likeRepository.existsByGuestIdAndRoomId(account.getId(), roomId)) {
			Like unLike = likeRepository.findByGuestIdAndRoomId(account.getId(), roomId);
			likeRepository.delete(unLike);
			//			いいねしてなかったら、テーブルに追加
		} else if (!likeRepository.existsByGuestIdAndRoomId(account.getId(), roomId)) {
			Like like = new Like(account.getId(), roomId);
			likeRepository.save(like);
		}
	}
}
