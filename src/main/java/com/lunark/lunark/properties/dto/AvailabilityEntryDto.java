package com.lunark.lunark.properties.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AvailabilityEntryDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;
    @Positive
    private double price;
    private boolean isReserved;
}
