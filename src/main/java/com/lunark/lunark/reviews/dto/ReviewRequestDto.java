package com.lunark.lunark.reviews.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequestDto {
    private int rating;
    private String description;
}
