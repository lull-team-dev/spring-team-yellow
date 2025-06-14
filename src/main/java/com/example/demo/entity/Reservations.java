package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservations {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "guest_id")
	private Guests guest;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private Rooms room;

	@Column(name = "total_price")
	private Integer totalPrice;

	@Column(name = "stay_nights")
	private Integer stayNights;

	//宿泊日
	@Column(name = "stay_date")
	private LocalDate stayDate;

	//予約日
	@Column(name = "reservation_on")
	private LocalDateTime reservationOn;

	//コンストラクタ
	public Reservations() {

	}

	public Reservations(Guests guest, Rooms room, Integer totalPrice, Integer stayNights, LocalDate stayDate) {
		this.guest = guest;
		this.room = room;
		this.totalPrice = totalPrice;
		this.stayNights = stayNights;
		this.stayDate = stayDate;
	}

	//メソッド
	public Guests getGuest() {
		return guest;
	}

	public void setGuest(Guests guest) {
		this.guest = guest;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setRoom(Rooms room) {
		this.room = room;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getStayNights() {
		return stayNights;
	}

	public void setStayNights(Integer stayNights) {
		this.stayNights = stayNights;
	}

	public LocalDate getStayDate() {
		return stayDate;
	}

	public void setStayDate(LocalDate stayDate) {
		this.stayDate = stayDate;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getReservationOn() {
		return reservationOn;
	}

}
