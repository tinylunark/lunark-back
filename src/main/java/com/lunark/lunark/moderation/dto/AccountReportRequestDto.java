package com.lunark.lunark.moderation.dto;

import com.lunark.lunark.validation.AccountExistsConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record AccountReportRequestDto(
        @NotNull
        @PastOrPresent
        LocalDateTime date,
        @NotNull
        @PositiveOrZero
        Long reportedId,
        @NotNull
        @NotEmpty
        String reason
) {
}
