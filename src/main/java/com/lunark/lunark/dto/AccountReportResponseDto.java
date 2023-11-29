package com.lunark.lunark.dto;

import com.lunark.lunark.model.Account;

import java.time.LocalDateTime;

public record AccountReportResponseDto (
    Long id,
    LocalDateTime date,
    Account reporter,
    Account reported
) {
}
