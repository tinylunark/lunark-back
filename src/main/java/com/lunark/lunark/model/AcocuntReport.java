package com.lunark.lunark.model;

import java.time.LocalDateTime;

public record AcocuntReport(
        Long id,
        LocalDateTime date,
        Account reporter,
        Account reported
) {
}
