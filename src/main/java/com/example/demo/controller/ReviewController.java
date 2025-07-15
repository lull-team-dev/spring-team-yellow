// ログインのみ
package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
			@Valid @ModelAttribute ReviewForm reviewForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (bindingResult.hasErrors()) {
			Reservation reservation = reservationRepository.findById(reviewForm.getReservationId()).orElseThrow();
			LocalDate checkoutDate = reservation.getStayDate().plusDays(reservation.getStayNights());

			model.addAttribute("reservation", reservation);
			model.addAttribute("checkoutDate", checkoutDate);

			return "review/createForm";
		}

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
		return "redirect:/rooms/" + reviewForm.getRoomId();
	}

	// 投稿したレビューの一覧表示
	@GetMapping("/myList")
	public String showMyList(
			Model model) {

		Integer loginGuestId = account.getId();
		List<Review> myReviews = reviewRepository.findByGuestIdOrderByCreatedAtDesc(loginGuestId);

		model.addAttribute("reviews", myReviews);

		return "review/myList";
	}

	// レビュー編集フォーム表示
	@GetMapping("/edit/{reviewId}")
	public String showEditForm(
			@PathVariable Integer reviewId,
			Model model) {

		Integer loginGuestId = account.getId();
		Review review = reviewRepository.findById(reviewId).orElseThrow();

		// ログイン中ユーザーがレビュー投稿者本人かチェック
		if (!review.getGuest().getId().equals(loginGuestId)) {
			return "redirect:/reservationHistory";
		}

		Reservation reservation = review.getReservation();
		Guest guest = review.getGuest();
		Room room = review.getRoom();

		// チェックアウト日を計算
		LocalDate stayDate = reservation.getStayDate();
		Integer stayNights = reservation.getStayNights();
		LocalDate checkoutDate = stayDate.plusDays(stayNights);

		// フォームにレビュー情報を詰める
		ReviewForm reviewForm = new ReviewForm();
		reviewForm.setGuestId(guest.getId());
		reviewForm.setRoomId(room.getId());
		reviewForm.setReservationId(reservation.getId());
		reviewForm.setRating(review.getRating());
		reviewForm.setComment(review.getComment());

		model.addAttribute("reservation", reservation);
		model.addAttribute("checkoutDate", checkoutDate);
		model.addAttribute("reviewForm", reviewForm);
		model.addAttribute("reviewId", reviewId);

		return "review/updateForm";
	}

	// レビュー更新処理
	@PostMapping("/update/{id}")
	public String updateReview(
			@PathVariable("id") Integer reviewId,
			@Valid @ModelAttribute ReviewForm reviewForm,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			Reservation reservation = reservationRepository.findById(reviewForm.getReservationId()).orElseThrow();
			LocalDate checkoutDate = reservation.getStayDate().plusDays(reservation.getStayNights());

			model.addAttribute("reservation", reservation);
			model.addAttribute("checkoutDate", checkoutDate);
			model.addAttribute("reviewId", reviewId);

			return "review/updateForm";
		}

		Review review = reviewRepository.findById(reviewId).orElseThrow();

		review.setRating(reviewForm.getRating());
		review.setComment(reviewForm.getComment());
		review.setUpdatedAt(LocalDateTime.now());

		reviewRepository.save(review);
		return "redirect:/rooms/" + reviewForm.getRoomId();
	}

	// レビュー削除
	@PostMapping("/delete")
	public String deleteReview(@RequestParam Integer reviewId) {

		Review review = reviewRepository.findById(reviewId).orElseThrow();
		Reservation reservation = review.getReservation();

		// 予約情報のレビューをNULLに
		reservation.setReview(null);
		reservationRepository.save(reservation);

		// 物理削除
		reviewRepository.deleteById(reviewId);

		return "redirect:/reviews/myList";
	}

}
