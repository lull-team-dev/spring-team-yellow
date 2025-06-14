package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Guests;

public interface GuestRepository extends JpaRepository<Guests, Integer> {

}
