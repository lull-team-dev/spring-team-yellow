package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "types")
public class Types {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "created_at")
	private LocalDate createdAt;

	//コンストラクタ
	public Types() {

	}

	public Types(String typeName) {
		this.typeName = typeName;
	}

	//メソッド
	public String getRoomName() {
		return typeName;
	}

	public void setRoomName(String typeName) {
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

}
