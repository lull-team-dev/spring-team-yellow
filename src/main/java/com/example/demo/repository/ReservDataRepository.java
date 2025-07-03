package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ReservData;

public interface ReservDataRepository extends JpaRepository<ReservData, Integer> {
	ReservData findByReservation_Id(Integer ReservationId);

}
