package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "guests")
public class Guests {

	//フィールド
	//GenerationType.IDENTITY は、データベースの自動インクリメント機能を使用して主キーを生成する
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;
	private String address;
	private String tel;
	private String password;

	@Column(name = "created_at")
	private LocalDate createdAt;

	//コンストラクタ
	public Guests() {

	}

	public Guests(String name, String email, String address, String tel, String password) {
		super();
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

	public Long getId() {
		return id;
	}

}
