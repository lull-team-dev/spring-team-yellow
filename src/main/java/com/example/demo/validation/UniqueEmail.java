package com.example.demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueEmailValid.class)
@Target({ ElementType.FIELD }) // フィールド（変数）にだけ使える
@Retention(RetentionPolicy.RUNTIME) // 実行時も保持される
public @interface UniqueEmail {

	//デフォルトのエラーメッセージ
	String message() default "このメールアドレスは使用できません";

	//バリデーショングループの使用
	Class<?>[] groups() default {};

	//メタ情報の付与(エラー処理の重さを指定できる）
	Class<? extends Payload>[] payload() default {};

}
