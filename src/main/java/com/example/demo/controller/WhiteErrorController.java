package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WhiteErrorController implements ErrorController {

	@RequestMapping("/error")
	public String errorBranch(HttpServletRequest request, Model model) {
		// ステータスコードの取得
		Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

		switch (statusCode) {
		case 404:
			model.addAttribute("errorImg", "/uploads/images/404.png");
			model.addAttribute("errorText", "申し訳ございません。<br>"
					+ "カピバラたちも一生懸命探しましたが、<br>"
					+ "お探しのページは海の向こうに行ってしまったようです。<br>"
					+ "下記のボタンから、素晴らしいリゾート体験をお楽しみください。");
			break;

		case 500:
			model.addAttribute("errorImg", "/uploads/images/505.png");
			model.addAttribute("errorText", "大変申し訳ございません。<br>"
					+ "サーバーに一時的な問題が発生しております。<br>"
					+ "カピバラたちが懸命にメンテナンスを行っておりますので、<br>"
					+ "しばらくお待ちいただいてから再度お試しください。");
			break;

		case 403:
			model.addAttribute("errorImg", "/uploads/images/403.png");
			model.addAttribute("errorText", "恐れ入ります。<br>"
					+ "こちらは当リゾートの会員様専用のプライベートエリアとなっております。<br>"
					+ "カピバラたちがそっと扉を閉じさせていただきます。<br>"
					+ "ぜひ他のエリアでお楽しみ下さい。");
			break;

		case 503:
			model.addAttribute("errorImg", "/uploads/images/503.png");
			model.addAttribute("errorText", "現在、より良いサービス提供のためシステムメンテナンスを<br>"
					+ "実施しております。<br>"
					+ "カピバラたちが心を込めて準備中です。<br>"
					+ "しばらくお待ちいただき、再度アクセスをお試しください。");
			break;

		case 502:
			model.addAttribute("errorImg", "/uploads/images/502.png");
			model.addAttribute("errorText", "申し訳ございません。サーバー間の通信に問題が発生しております。<br>"
					+ "カピバラたちが懸命に通信回復作業を行っておりますので、少し時間をおいてから再度お試しください。");
			break;

		default:
			model.addAttribute("errorImg", "/uploads/images/000.png");
			model.addAttribute("errorText", "申し訳ございません。カピバラたちも初めて遭遇する\n"
					+ "珍しい出来事が発生してしまいました。\n"
					+ "現在、のんびり屋のカピバラたちが慌てながらも\n"
					+ "一生懸命に原因を調査しております。");
			break;
		}

		model.addAttribute("statusCode", statusCode);
		return "white-error";
	}
}
