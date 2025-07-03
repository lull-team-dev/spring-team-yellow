package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
	List<Guest> findByEmailAndPassword(String email, String password);

}
