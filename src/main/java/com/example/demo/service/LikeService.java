package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Like;
import com.example.demo.entity.Room;
import com.example.demo.model.Account;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class LikeService {
	@Autowired
	Account account;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	RoomRepository roomRepository;

	//いいねされてる部屋のidを取得
	public List<Integer> likeIcon() {
		List<Like> likes = likeRepository.findByGuestId(account.getId());
		List<Integer> likeRoom = new ArrayList<>();
		for (Like like : likes) {
			likeRoom.add(like.getRoomId());
		}
		return likeRoom;
	}

	//	いいね押した時の処理
	public boolean toggleLike(Integer guestId, Integer roomId) {
		//		いいねされてたら、テーブルから削除
		if (likeRepository.existsByGuestIdAndRoomId(account.getId(), roomId)) {
			Like unLike = likeRepository.findByGuestIdAndRoomId(account.getId(), roomId);
			likeRepository.delete(unLike);
			return false;
			//			いいねしてなかったら、テーブルに追加
		} else {
			Like like = new Like(account.getId(), roomId);
			likeRepository.save(like);
			return true;
		}
	}

	//	いいねした部屋を取得
	public List<Room> getLikedRooms(Integer guestId) {
		List<Like> likes = likeRepository.findByGuestId(account.getId());
		List<Room> rooms = new ArrayList<>();
		for (Like like : likes) {
			//			（= nullじゃなければ）、rooms リストに追加する
			//			rooms::add は room -> rooms.add(room) と同じ意味。
			roomRepository.findById(like.getRoomId()).ifPresent(rooms::add);
		}
		return rooms;
	}

}
