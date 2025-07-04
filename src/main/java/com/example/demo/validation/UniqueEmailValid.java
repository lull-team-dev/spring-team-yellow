package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.repository.GuestRepository;

@Component // SpringのBeanとしてDIで利用
public class UniqueEmailValid implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private GuestRepository guestRepository; // DBに「そのメールが存在するか？」を問合せるため

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		// 空文字やnullはここでは判定しない（他の@NotBlank/@Emailなどで判定する想定）
		if (email == null || email.isBlank()) {
			return true;
		}

		// GuestRepositoryのexistsByEmailで「既に使われている？」を判定
		return !guestRepository.existsByEmail(email);
		// 既にDBにあったらfalse（バリデーションエラー）、なければtrue（OK）
	}
}