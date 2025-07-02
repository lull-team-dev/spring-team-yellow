package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.repository.ReservDataRepository;

@Service
public class RoomService {

	@Autowired
	Account account;
	@Autowired
	ReservDataRepository reservDataRepository;

	//選択範囲の日付をリストにひとつずつ格納
	public List<LocalDate> dateCalc(LocalDate checkinDate, LocalDate checkoutDate) {
		List<LocalDate> stayDates = new ArrayList<>();

		Integer days = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);

		for (Integer addDays = 0; addDays < days; addDays++) {
			stayDates.add(checkinDate.plusDays(addDays));
		}

		return stayDates;
	}

	//予約済みの部屋じゃないかのチェック
	public boolean isRoomAvailable(
			Integer roomId,
			List<LocalDate> stayDates) {
		boolean roomExists = reservDataRepository.existsByReservationRoomIdAndStayOneDateIn(roomId, stayDates);

		return roomExists;
	}

	//宿泊者が同日に予約していないかのチェック
	public boolean hasGuestReservedOnDates(List<LocalDate> stayDates) {
		boolean guestExists = reservDataRepository.existsByReservationGuestIdAndStayOneDateIn(account.getId(),
				stayDates);

		return guestExists;
	}
}
