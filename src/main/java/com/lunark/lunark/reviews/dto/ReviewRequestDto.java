package com.lunark.lunark.reviews.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequestDto {
    @Min(value = 1, message = "Rating must be between 1 and 5, inclusive")
    @Max(value = 5, message = "Rating must be between 1 and 5, inclusive")
    private int rating;
    @NotBlank
    @Pattern(message="Reviews can only contain alphanumeric characters and punctuation marks", regexp = "[a-zA-Z0-9 \\.!\\?\'\"]+")
    private String description;
}
