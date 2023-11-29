package com.lunark.lunark.dto;

import com.lunark.lunark.model.Account;

import java.time.LocalDateTime;

public record AccountReportRequestDto(
        LocalDateTime date,
        Long reporterId,
        Long reportedId
) {
}
