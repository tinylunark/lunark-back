package com.lunark.lunark.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AvailabilityEntryDto {
    private LocalDate date;
    private double price;
}
