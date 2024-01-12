package com.lunark.lunark.reports.dto;

import com.lunark.lunark.reservations.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GeneralReportResponseDto {
    Long reservationCount;
    Double profit;
}
