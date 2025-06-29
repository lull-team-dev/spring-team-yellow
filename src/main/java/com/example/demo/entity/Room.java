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
	private Integer id;

	@Column(name = "room_name")
	private String roomName;

	private Integer price;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private Type type;

	@Column(name = "img_path")
	private String imgPath;

	@Column(name = "img_path2")
	private String imgPath2;

	//説明文
	private String description;

	@Column(name = "created_at")
	private LocalDate createdAt;

	//コンストラクタ
	public Room() {
	}

	public Room(String roomName, Integer price, Type type, String imgPath, String imgPath2, String description) {
		this.roomName = roomName;
		this.price = price;
		this.type = type;
		this.imgPath = imgPath;
		this.imgPath2 = imgPath2;
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
	
	public String getImgPath2() {
		return imgPath2;
	}

	public void setImgPath2(String imgPath2) {
		this.imgPath2 = imgPath2;
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

	public Integer getId() {
		return id;
	}

}
