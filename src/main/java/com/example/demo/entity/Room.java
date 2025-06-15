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
public class Room {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "room_name")
	private String roomName;

	private Integer price;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private Type type;

	@Column(name = "img_path")
	private String imgPath;

	//説明文
	private String description;

	@Column(name = "created_at")
	private LocalDate createdAt;

	//コンストラクタ
	public Room() {
		super();
	}

	public Room(String roomName, Integer price, Type type, String imgPath, String description) {
		super();
		this.roomName = roomName;
		this.price = price;
		this.type = type;
		this.imgPath = imgPath;
		this.description = description;
	}

	//メソッド
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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
