package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.example.demo.validation.UniqueEmail;

public class reserveDto {

	@NotBlank(message = "ユーザー名を入力してください")
	@Size(max = 15, message = "ユーザー名は15文字以内で入力してください")
	private String guestName;

	//ユニークエラーアノテーションつくる。
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が正しくありません")
	@Size(max = 100, message = "メールアドレスは100文字以内で入力してください")
	@UniqueEmail
	private String email;

	@NotBlank(message = "電話番号を入力してください")
	@Size(max = 15, message = "電話番号は15文字以内で入力してください")
	@Pattern(regexp = "^[0-9]+$", message = "電話番号は数字のみで入力してください")
	//Pattern(regexp = "^[0-9\\-]+$", message = "電話番号は半角数字とハイフンで入力してください")
	private String tel;

	@NotBlank(message = "住所を入力してください")
	@Size(max = 120, message = "住所は120文字以内で入力してください")
	private String address;

	//すでに予約されている場合は省くアノテーション作成
	@NotNull(message = "チェックイン日を入力してください")
	private LocalDate checkinDate;

	@NotNull(message = "チェックアウト日を入力してください")
	private LocalDate checkoutDate;

	@NotNull(message = "宿泊者数を入力してください")
	private Integer guestCount;

	//メソッド
	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Integer getGuestCount() {
		return guestCount;
	}

	public void setGuestCount(Integer guestCount) {
		this.guestCount = guestCount;
	}

}
