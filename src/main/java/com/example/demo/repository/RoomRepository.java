package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Room;
import com.example.demo.entity.Type;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	List<Room> findByRoomNameContainingAndTypeInAndPriceBetween(String keyword, List<Type> typeList, Integer underPrice,
			Integer upPrice);

	List<Room> findByRoomNameContainingAndPriceBetween(String keyword, Integer underPrice, Integer upPrice);

	List<Room> findByTypeInAndPriceBetween(List<Type> typeList, Integer underPrice, Integer upPrice);

}
