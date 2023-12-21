package com.lunark.lunark.moderation.dto;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reviews.model.Review;

import java.time.LocalDateTime;

public record ReviewReportResponseDto(
        Long id,
        LocalDateTime date,
        Account reporter,
        Review review
) {
}
