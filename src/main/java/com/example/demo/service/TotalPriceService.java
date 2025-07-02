package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class TotalPriceService {

	public Integer calcTotalPrice(Integer roomPrice, Integer planPrice) {
		Integer totalPrice = roomPrice + planPrice;
		return totalPrice;
	}
}
