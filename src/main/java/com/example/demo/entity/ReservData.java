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
@Table(name = "reservation_details")
public class ReservData {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "stay_one_date")
	private LocalDate stayOneDate;

	@Column(name = "price_per_day")
	private Integer pricePerDay;

	@ManyToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	//コンストラクタ
	public ReservData(LocalDate stayOneDate, Integer pricePerDay, Reservation reservation) {
		this.stayOneDate = stayOneDate;
		this.pricePerDay = pricePerDay;
		this.reservation = reservation;
	}

	public ReservData() {

	}

	//フィールド
	public LocalDate getStayOneDate() {
		return stayOneDate;
	}

	public void setStayOneDate(LocalDate stayOneDate) {
		this.stayOneDate = stayOneDate;
	}

	public Integer getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Integer pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Integer getId() {
		return id;
	}

}
