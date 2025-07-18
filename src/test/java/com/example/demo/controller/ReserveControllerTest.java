package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@ExtendWith(MockitoExtension.class)
public class ReserveControllerTest {

	//クラス内全てのテストコード実行前準備
	private ReserveController reserveController;
	private RedirectAttributes redirectAttributes;

	@BeforeEach
	void setup() {
		reserveController = new ReserveController();
		redirectAttributes = new RedirectAttributesModelMap();
	}

	//テストコード
	@Test
	@DisplayName("statusが2の時はリダイレクト")
	void testRedirectWhenStatusIs2() {

		// リクエストパラメータの順番
		String viewName = reserveController.reserveConf(
				1, "名前", "mail@test.com", "000", "住所",
				1, 2,
				LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 3),
				2, // ← status = 2 を渡す
				redirectAttributes);

		// テストの実行
		assertEquals("redirect:/rooms/{id}/reserve", viewName);
	}
}
