// アクセス制限処理用AOP
package com.example.demo.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.model.Account;

@Aspect
@Component
public class CheckLoginAspect {

	@Autowired
	Account account;

	// ログ出力処理
	// 全Controllerクラスの全メソッド処理前を指定
	@Before("execution(* com.example.demo.controller.*Controller.*(..))")
	public void writeLog(JoinPoint jp) {

		// ログインしたアカウント情報を取得
		if (account.isLoggedIn()) {
			System.out.print("アカウントID_" + account.getId() + "：");
		} else {
			System.out.print("未ログイン：");
		}

		System.out.println(jp.getSignature());
	}

	// 未ログインの場合ログインページにリダイレクト
	@Around("execution(* com.example.demo.controller..*.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {
		String className = jp.getSignature().getDeclaringTypeName();

		// ログイン不要なコントローラを除外
		if (className.endsWith("LoginController") ||
				className.endsWith("AccountController") ||
				className.endsWith("DetailController") ||
				className.endsWith("RoomController") ||
				className.contains("HotelTopController")) {
			return jp.proceed();
		}

		// 未ログインならリダイレクト
		if (!account.isLoggedIn()) {

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attr.getRequest();
			HttpServletResponse response = attr.getResponse();

			// Ajaxリクエストかどうかを判定
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				response.sendError(401, "Unauthorized");
				return null; // Ajaxにはエラーステータスを返して終了
			}

			System.err.println("ログインしていません!");

			return "redirect:/login?error=notLoggedIn";
		}

		return jp.proceed();
	}

}
