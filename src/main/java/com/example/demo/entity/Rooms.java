package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Rooms {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Integer price;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private Types type;

	@Column(name = "img_path")
	private String imgPath;

	private String description;

	@Column(name = "created_at")
	private LocalDate createdAt;

	//コンストラクタ
	public Rooms() {
		super();
	}

	public Rooms(String name, Integer price, Types type, String imgPath, String description) {
		super();
		this.name = name;
		this.price = price;
		this.type = type;
		this.imgPath = imgPath;
		this.description = description;
	}

	//メソッド
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

}
