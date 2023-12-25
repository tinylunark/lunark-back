package com.lunark.lunark.reviews.dto;

import com.lunark.lunark.reviews.model.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewDto {

    private int rating;
    private String description;
    private LocalDateTime date;

    public Review toReview(String reviewType){
        return new Review(null,
                this.rating,
                this.description,
                false,
                this.date,
                Review.ReviewType.valueOf(reviewType.toUpperCase()));
    }
}
