package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Room;
import com.example.demo.entity.Type;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	//upPrice < 100000
	List<Room> findByRoomNameContainingAndTypeInAndPriceBetween(String keyword, List<Type> typeList, Integer underPrice,
			Integer upPrice);

	List<Room> findByRoomNameContainingAndPriceBetween(String keyword, Integer underPrice, Integer upPrice);

	List<Room> findByTypeInAndPriceBetween(List<Type> typeList, Integer underPrice, Integer upPrice);

	List<Room> findByPriceBetween(Integer underPrice, Integer upPrice);

	//upPrice == 100000
	List<Room> findByRoomNameContainingAndTypeInAndPriceGreaterThanEqual(String keyword, List<Type> typeList,
			Integer underPrice);

	List<Room> findByRoomNameContainingAndPriceGreaterThanEqual(String keyword, Integer underPrice);

	List<Room> findByTypeInAndPriceGreaterThanEqual(List<Type> typeList, Integer underPrice);

	List<Room> findByPriceGreaterThanEqual(Integer underPrice);

	//修正中
	List<Room> findByIdNotInAndRoomNameContainingAndTypeInAndPriceGreaterThanEqual(List<Integer> roomIds,
			String keyword, List<Type> typeList, Integer underPrice);

	List<Room> findByIdNotInAndRoomNameContainingAndPriceGreaterThanEqual(List<Integer> roomIds, String keyword,
			Integer underPrice);

	List<Room> findByIdNotInAndTypeInAndPriceGreaterThanEqual(List<Integer> roomIds, List<Type> typeList,
			Integer underPrice);

	List<Room> findByIdNotInAndPriceGreaterThanEqual(List<Integer> roomIds, Integer underPrice);

	//else修正
	List<Room> findByIdNotInAndRoomNameContainingAndTypeInAndPriceBetween(List<Integer> roomIds, String keyword,
			List<Type> typeList, Integer underPrice, Integer upPrice);

	List<Room> findByIdNotInAndRoomNameContainingAndPriceBetween(List<Integer> roomIds, String keyword,
			Integer underPrice, Integer upPrice);

	List<Room> findByIdNotInAndTypeInAndPriceBetween(List<Integer> roomIds, List<Type> typeList, Integer underPrice,
			Integer upPrice);

	List<Room> findByIdNotInAndPriceBetween(List<Integer> roomIds, Integer underPrice, Integer upPrice);

}
