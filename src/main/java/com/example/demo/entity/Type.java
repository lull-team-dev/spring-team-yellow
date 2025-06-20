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
public class Type {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "created_at")
	private LocalDate createdAt;

	//コンストラクタ
	public Type() {

	}

	public Type(String typeName) {
		this.typeName = typeName;
	}

	//メソッド
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

}
