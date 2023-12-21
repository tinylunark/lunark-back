package com.lunark.lunark.reservations.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {
    @NotNull
    Long propertyId;
    @Future
    LocalDate startDate;
    @Future
    LocalDate endDate;
    @Positive
    int numberOfGuests;
}
