package com.lunark.lunark.reservations.dto;

import com.lunark.lunark.reservations.model.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservationSearchDto {
    String propertyName;
    LocalDate startDate;
    LocalDate endDate;
    ReservationStatus status;
    Long accountId;
}
