// ログインのみ
package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.ReviewForm;
import com.example.demo.entity.Guest;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Review;
import com.example.demo.entity.Room;
import com.example.demo.model.Account;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.RoomRepository;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private Account account;

	// レビュー投稿フォーム表示
	@GetMapping("/new/{reservationId}")
	public String showCreateForm(
			@PathVariable Integer reservationId,
			Model model) {

		Integer loginGuestId = account.getId();
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

		// ログイン中ユーザーが予約者本人かチェック
		if (!reservation.getGuest().getId().equals(loginGuestId)) {
			// メッセージ添える？
			return "redirect:/reservationHistory";
		}

		Guest guest = reservation.getGuest();
		Room room = reservation.getRoom();

		//チェックアウト日を計算
		LocalDate stayDate = reservation.getStayDate(); //チェックイン
		Integer stayNights = reservation.getStayNights(); //宿泊日数
		LocalDate checkoutDate = stayDate.plusDays(stayNights);//チェックアウト

		ReviewForm reviewForm = new ReviewForm();
		reviewForm.setGuestId(guest.getId());
		reviewForm.setRoomId(room.getId());
		reviewForm.setReservationId(reservation.getId());

		model.addAttribute("reservation", reservation);
		model.addAttribute("checkoutDate", checkoutDate);
		model.addAttribute("reviewForm", reviewForm);

		return "review/createForm";
	}

	// レビュー投稿処理
	@PostMapping("/create")
	public String createReview(
			@ModelAttribute ReviewForm reviewForm,
			RedirectAttributes redirectAttributes) {

		Guest guest = guestRepository.findById(reviewForm.getGuestId()).orElseThrow();
		Room room = roomRepository.findById(reviewForm.getRoomId()).orElseThrow();
		Reservation reservation = reservationRepository.findById(reviewForm.getReservationId()).orElseThrow();

		Review review = new Review();
		review.setGuest(guest);
		review.setRoom(room);
		review.setReservation(reservation);
		review.setRating(reviewForm.getRating());
		review.setComment(reviewForm.getComment());
		review.setCreatedAt(LocalDateTime.now());

		reviewRepository.save(review);
		return "redirect:/room";
	}

	// レビュー編集フォーム表示
	@GetMapping("/edit/{id}")
	public String showEditForm() {
		return "review";
	}

	// レビュー更新処理
	@PostMapping("/update/{id}")
	public String updateReview() {
		return "review";
	}

	// レビュー削除
	@PostMapping("/delete/{id}")
	public String deleteReview() {
		return "review";
	}

}
