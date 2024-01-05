package com.lunark.lunark.reservations.dto;

import com.lunark.lunark.reservations.model.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationSearchDto {
    String propertyName;
    LocalDate startDate;
    LocalDate endDate;
    ReservationStatus status;
}
