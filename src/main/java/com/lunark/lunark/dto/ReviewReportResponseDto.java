package com.lunark.lunark.dto;

import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.Review;

import java.time.LocalDateTime;

public record ReviewReportResponseDto(
        Long id,
        LocalDateTime date,
        Account reporter,
        Review review
) {
}
