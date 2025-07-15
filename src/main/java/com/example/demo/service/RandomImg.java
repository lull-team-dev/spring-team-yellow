package com.example.demo.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomImg {

	public String showImg() {
		Random random = new Random();
		int randomValue = random.nextInt(5);

		String randomImg = null;
		switch (randomValue) {
		case 0:
			randomImg = "/uploads/images/search-logo.png";
			break;

		case 1:
			randomImg = "/uploads/images/search-logo2.png";
			break;

		case 2:
			randomImg = "/uploads/images/search-logo3.png";
			break;

		case 3:
			randomImg = "/uploads/images/search-logo4.png";
			break;

		default:
			randomImg = "/uploads/images/search-logo5.png";
			break;
		}
		return randomImg;
	}

}
