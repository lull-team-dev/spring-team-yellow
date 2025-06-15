package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Type;

public interface TypeRepository extends JpaRepository<Type, Integer> {

	List<Type> findByIdIn(List<Integer> types);

}
