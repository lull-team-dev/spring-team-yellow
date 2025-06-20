package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "guest_id")
	private Guest guest;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

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

	//「親（Reservation）に対して行った操作（保存・更新・削除など）を子（ReservData）にもすべて適用する
	//「親から外された子（孤児）をDBから自動で削除する
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservData> reservData = new ArrayList<>();

	//コンストラクタ
	public Reservation() {

	}

	public Reservation(Guest guest, Room room, Integer totalPrice, Integer stayNights, LocalDate stayDate) {
		this.guest = guest;
		this.room = room;
		this.totalPrice = totalPrice;
		this.stayNights = stayNights;
		this.stayDate = stayDate;
	}

	//メソッド
	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
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

	public Integer getId() {
		return id;
	}

	public LocalDateTime getReservationOn() {
		return reservationOn;
	}

}
