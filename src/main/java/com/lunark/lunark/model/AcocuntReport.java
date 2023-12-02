package com.lunark.lunark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public record AcocuntReport(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        LocalDateTime date,
        Account reporter,
        Account reported
) {
}
