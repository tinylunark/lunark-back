package com.lunark.lunark.moderation.dto;

import com.lunark.lunark.auth.model.Account;

import java.time.LocalDateTime;

public record AccountReportResponseDto (
    Long id,
    LocalDateTime date,
    Long reporterId,
    Long reportedId,
    String reason
) {
}
