package com.lunark.lunark.moderation.dto;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reviews.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReportResponseDto {
    private Long id;
    private LocalDateTime date;
    private Long reporterId;
    private Long reviewId;
}