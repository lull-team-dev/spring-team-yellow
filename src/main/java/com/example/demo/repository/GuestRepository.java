package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
	List<Guest> findByEmailAndPassword(String email, String password);

	//メールアドレスがあるかの判定
	boolean existsByEmail(String email);

	// メールアドレスの重複チェック用クエリメソッド
	Optional<Guest> findByEmail(String email);

}
