package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// レビュー投稿フォームDTO
public class ReviewForm {

	@NotNull
	private Integer guestId;

	@NotNull
	private Integer roomId;

	@NotNull
	private Integer reservationId;

	@NotNull(message = "評価は必須です")
	@Min(value = 1, message = "評価は1以上で入力してください")
	@Max(value = 5, message = "評価は5以下で入力してください")
	private Integer rating;

	@Size(max = 400, message = "コメントは400文字以内で入力してください")
	private String comment;

	// ゲッター・セッター
	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
