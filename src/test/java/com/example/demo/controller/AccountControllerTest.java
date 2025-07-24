package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("登録画面が正しく表示される")
	void testAccountIndex() throws Exception {
		mockMvc.perform(get("/account/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"));
	}

	@Test
	@DisplayName("必須項目が空の場合エラーになる")
	void testValidation_requiredFieldsEmpty() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "")
				.param("email", "")
				.param("address", "")
				.param("tel", "")
				.param("password", "")
				.param("password_confirm", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrors("guest", "name", "email", "address", "tel", "password"));
	}

	@Test
	@DisplayName("名前が15文字以上の場合エラーになる")
	void testValidation_nameTooLong() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "ふじもとたろうきざえもんのしょうときのり")
				.param("email", "test@test.com")
				.param("address", "東京都")
				.param("tel", "0123456789")
				.param("password", "test1234")
				.param("password_confirm", "test1234"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrors("guest", "name"));
	}

	@Test
	@DisplayName("メールアドレスの形式が不正な場合エラーになる")
	void testValidation_invalidEmailFormat() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト")
				.param("email", "test.co.jp")
				.param("address", "東京都")
				.param("tel", "0123456789")
				.param("password", "test1234")
				.param("password_confirm", "test1234"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrors("guest", "email"));
	}

	@Test
	@DisplayName("メールアドレスが100文字以上の場合エラーになる")
	void testValidation_emailTooLong() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト")
				.param("email",
						"fujimototaroukizaemonnosyoutokinori_super_long_example_address_for_testing_purposes_because_we_need_over_100_characters@example.com")
				.param("address", "東京都")
				.param("tel", "0123456789")
				.param("password", "test1234")
				.param("password_confirm", "test1234"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrors("guest", "email"));
	}

	@Test
	@DisplayName("住所が120文字以上の場合エラーになる")
	void testValidation_addressTooLong() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト")
				.param("email", "test@test.com")
				.param("address",
						"フィリピン共和国メトロマニラ首都圏マカティ市サンイシドロ地区フェーズ3インダストリアルゾーン7番アベニューエメラルドグリーンタワー通りグリーンパークテクノロジービレッジ東エリアセクション5ビルディングネクサスC棟4649階南南東ウィングAAA号室")
				.param("tel", "0123456789")
				.param("password", "test1234")
				.param("password_confirm", "test1234"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrors("guest", "address"));
	}

	@Test
	@DisplayName("電話番号が10～15桁以外の場合エラーになる")
	void testValidation_invalidTel() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト")
				.param("email", "test@test.com")
				.param("address", "東京都")
				.param("tel", "11111111111111111111")
				.param("password", "test1234")
				.param("password_confirm", "test1234"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrors("guest", "tel"));
	}

	@Test
	@DisplayName("パスワードが英数字含む5～100文字でない場合エラーになる")
	void testValidation_invalidPassword() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト")
				.param("email", "test@test.com")
				.param("address", "東京都")
				.param("tel", "0123456789")
				.param("password", "test")
				.param("password_confirm", "test"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrors("guest", "password"));
	}

	@Test
	@DisplayName("パスワード確認が一致しない場合エラーになる")
	void testValidation_passwordMismatch() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト")
				.param("email", "test@test.com")
				.param("address", "東京都")
				.param("tel", "0123456789")
				.param("password", "test1234")
				.param("password_confirm", "different"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeExists("error"));
	}

	@Test
	@DisplayName("メール重複チェックに引っかかる場合エラーになる")
	void testValidation_emailDuplicate() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト")
				.param("email", "testuser@com")
				.param("address", "東京都")
				.param("tel", "0123456789")
				.param("password", "test1234")
				.param("password_confirm", "test1234"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().attributeExists("error"));
	}

	@Test
	@DisplayName("正常なデータなら保存されてリダイレクトする")
	void testRegisterSuccess() throws Exception {
		mockMvc.perform(post("/account/register")
				.param("name", "テスト・D・テスト")
				.param("email", "testtest@test.com")
				.param("address", "東京都渋谷区渋谷３丁目１０−１３ Tokyu Reit 渋谷Rビル B1")
				.param("tel", "0364573780")
				.param("password", "testuser123")
				.param("password_confirm", "testuser123"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}
}