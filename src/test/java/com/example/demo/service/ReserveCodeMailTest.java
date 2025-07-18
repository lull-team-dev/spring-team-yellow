package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.entity.Guest;
import com.example.demo.entity.Plan;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Room;

public class ReserveCodeMailTest {

	@Test
	@DisplayName("メール送信用テキストが作成される")
	void testBuildWelcomeMessage_normal() {
		Reservation reserve = new Reservation();
		Guest guest = new Guest();
		guest.setName("テスト太郎");
		reserve.setGuest(guest);
		reserve.setStayDate(LocalDate.of(2025, 8, 1));
		reserve.prePersist();
		reserve.setStayNights(2);
		Room room = new Room();
		room.setRoomName("オーションビュースイート");
		reserve.setRoom(room);
		Plan plan = new Plan();
		plan.setName("朝食付き");
		plan.setDetails("朝食ビュッフェ");
		reserve.setPlan(plan);
		reserve.setTotalPrice(10000);

		ReserveCodeMail mail = new ReserveCodeMail();
		String result = mail.buildWelcomeMessage(reserve);

		//nullチェック
		assertNotNull(result);
		//ゲストネームが含まれてるかチェック（ゲストオブジェクトある？）
		assertTrue(result.contains("テスト太郎"));
		//
		assertTrue(result.contains("オーションビュースイート"));
	}

	@Test
	@DisplayName("メール送信用テキストの中がnullで作成が失敗")
	void testBuildWelcomeMessage_guestWithNull_shouldReturnNull() {
		ReserveCodeMail mail = new ReserveCodeMail();
		Reservation reserve = new Reservation(); // 中身がnull

		String result = mail.buildWelcomeMessage(reserve);

		assertNull(result); // try-catchでnullが返る想定
	}

}
