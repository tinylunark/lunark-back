package com.lunark.lunark.model;

import java.time.LocalDateTime;

public record ReviewReport(
        Long id,
        LocalDateTime date,
        Review review
) {}