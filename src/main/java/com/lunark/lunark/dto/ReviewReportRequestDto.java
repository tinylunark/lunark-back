package com.lunark.lunark.dto;

import java.time.LocalDateTime;

public record ReviewReportRequestDto(
        LocalDateTime date,
        Long reporterId,
        Long reviewId
) {
}
