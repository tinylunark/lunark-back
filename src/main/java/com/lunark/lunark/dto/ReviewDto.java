package com.lunark.lunark.dto;

import com.lunark.lunark.model.Review;

import java.time.LocalDateTime;

public class ReviewDto {

    private int rating;
    private String desc;
    private LocalDateTime date;

    public ReviewDto(int rating, String desc, LocalDateTime date) {
        this.rating = rating;
        this.desc = desc;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Review toReview(String reviewType){
        return new Review(null,
                this.rating,
                this.desc,
                false,
                this.date,
                Review.ReviewType.valueOf(reviewType.toUpperCase()));
    }
}
