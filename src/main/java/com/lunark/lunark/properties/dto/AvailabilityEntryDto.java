package com.lunark.lunark.properties.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AvailabilityEntryDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private double price;
}
