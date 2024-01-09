package com.lunark.lunark.moderation.dto;

import java.time.LocalDateTime;

public record AccountReportRequestDto(
        LocalDateTime date,
        Long reportedId,
        String reason
) {
}
