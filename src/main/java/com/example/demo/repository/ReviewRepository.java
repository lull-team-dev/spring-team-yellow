package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	// 部屋ごとのレビュー一覧
	List<Review> findByRoomIdOrderByCreatedAtDesc(Integer roomId);

	// ユーザーごとのレビュー一覧
	List<Review> findByGuestIdOrderByCreatedAtDesc(Integer guestId);

	// 予約に対してレビュー済みか確認
	boolean existsByReservationId(Integer reservationId);

	// レビューの平均値を取得
	@Query("SELECT AVG(r.rating) FROM Review r WHERE r.room.id = :roomId")
	Double findAverageRatingByRoomId(@Param("roomId") Integer roomId);

}
