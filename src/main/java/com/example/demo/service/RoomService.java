package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

	public List<LocalDate> dateCalc(LocalDate checkinDate, LocalDate checkoutDate) {
		List<LocalDate> stayDates = new ArrayList<>();

		//まだ計算途中　ストリームを使うかかくちょ

		return stayDates;
	}
}
