package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

	public List<LocalDate> dateCalc(LocalDate checkinDate, LocalDate checkoutDate) {
		List<LocalDate> stayDates = new ArrayList<>();

		Integer days = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);

		for (Integer addDays = 1; addDays < days - 1; addDays++) {
			stayDates.add(checkinDate.plusDays(addDays));
		}

		return stayDates;
	}
}
