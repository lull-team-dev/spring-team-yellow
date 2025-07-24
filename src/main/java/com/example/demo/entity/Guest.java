package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// バリデーションライブラリのインポート
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "guests")
public class Guest {

	//フィールド
	//GenerationType.IDENTITY は、データベースの自動インクリメント機能を使用して主キーを生成する
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// @NotBlankは、NULL・空文字・空白・" " 全てNG
	@NotBlank(message = "ユーザー名を入力してください")
	@Size(max = 15, message = "ユーザー名は15文字以内で入力してください")
	private String name;

	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が正しくありません")
	@Size(max = 100, message = "メールアドレスは100文字以内で入力してください")
	private String email;

	@NotBlank(message = "住所を入力してください")
	@Size(max = 120, message = "住所は120文字以内で入力してください")
	private String address;

	// @Patternは、設定したルール(正規表現)に合ってるかのチェック → 10〜15桁の数字でなければエラー
	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "^[0-9]{10,15}$", message = "電話番号は10〜15桁の数字で入力してください")
	private String tel;

	@NotBlank(message = "パスワードを入力してください")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{5,100}$", message = "パスワードは英数字を含む5～100文字で入力してください")
	private String password;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDate createdAt;

	//コンストラクタ
	public Guest() {

	}

	public Guest(String name, String email, String address, String tel, String password) {
		this.name = name;
		this.email = email;
		this.address = address;
		this.tel = tel;
		this.password = password;
	}

	//メソッド
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
