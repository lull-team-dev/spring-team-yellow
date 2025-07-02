package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	//テーブルに存在するか（存在すればtrue,なければfalse）
	boolean existsByGuestIdAndRoomId(Integer guestId, Integer roomId);

	//テーブルに存在するか（削除処理）
	void deleteByGuestIdAndRoomId(Integer guestId, Integer roomId);

	List<Like> findByGuestId(Integer guestId);

	Like findByGuestIdAndRoomId(Integer guestId, Integer roomId);

}
