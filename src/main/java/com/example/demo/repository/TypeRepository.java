package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Type;

public interface TypeRepository extends JpaRepository<Type, Integer> {

}
