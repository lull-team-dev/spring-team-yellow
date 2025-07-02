package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	List<Reservation> findByReservDatasStayOneDateIn(List<LocalDate> stayDates);

}
