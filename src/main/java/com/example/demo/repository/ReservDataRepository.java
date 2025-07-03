package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ReservData;

public interface ReservDataRepository extends JpaRepository<ReservData, Integer> {

	ReservData findByReservation_Id(Integer ReservationId);

	//ルームの予約状況チェック
	boolean existsByReservationRoomIdAndStayOneDateIn(Integer roomId, List<LocalDate> stayDates);

	//宿泊者の予約状況チェック
	boolean existsByReservationGuestIdAndStayOneDateIn(Integer id, List<LocalDate> stayDates);

	List<ReservData> findByReservationGuestIdAndStayOneDateIn(Integer id,
			List<LocalDate> stayDates);

}
