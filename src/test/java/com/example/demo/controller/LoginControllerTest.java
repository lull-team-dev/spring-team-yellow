package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import com.example.demo.model.Account;
import com.example.demo.repository.GuestRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	@InjectMocks
	private LoginController loginController;

	@Mock
	private GuestRepository guestRepository;

	@Mock
	private Model model;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	Account account;

	@Mock
	HttpSession session;

	//	メールアドレス空欄
	@Test
	void testEmailIsEmpty() {
		String view = loginController.login("", "password123", model);//パスワードは有効な値として設定
		verify(model).addAttribute("emailError", "メールアドレスは必須です");//モック化された model が、コントローラー内で addAttribute を呼ばれたかどうかを検証
		assertEquals("login", view);//エラーがあったときにログイン画面に戻ることを確認
	}

	//	パスワード空欄
	@Test
	void testPasswordIsEmpty() {
		String view = loginController.login("test@example.com", "", model);
		verify(model).addAttribute("passwordError", "パスワードは必須です");
		assertEquals("login", view);
	}

	//ログイ失敗時の動作（パスワードもメールアドレスも間違っている）
	@Test
	void testLoginFailure() {
		when(guestRepository.findByEmailAndPassword("acc@mail.com", "pp1111"))//この条件で呼び出されたら
				.thenReturn(Collections.emptyList());//空のリストを返す（DBに値がないという設定）
		String view = loginController.login("acc@mail.com", "pp1111", model);
		verify(model).addAttribute("loginError", "メールアドレスまたはパスワードが違います");
		assertEquals("login", view);
	}

}
