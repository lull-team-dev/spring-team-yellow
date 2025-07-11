package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByRoomIdAndDeletedAtIsNullOrderByCreatedAtDesc(Integer roomId);

	List<Review> findByGuestIdAndDeletedAtIsNullOrderByCreatedAtDesc(Integer guestId);

	@Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId AND r.room.id = :roomId AND r.stayDate < CURRENT_DATE")
	List<Reservation> findPastReservations(Integer guestId, Integer roomId);

	@Query("SELECT AVG(r.rating) FROM Review r WHERE r.room.id = :roomId AND r.deletedAt IS NULL")
	Double findAverageRatingByRoomId(@Param("roomId") Integer roomId);

}
