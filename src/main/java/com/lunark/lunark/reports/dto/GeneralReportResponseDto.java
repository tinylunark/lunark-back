package com.lunark.lunark.reports.dto;

import com.lunark.lunark.reservations.dto.ReservationDto;
import lombok.Data;

import java.util.List;

@Data
public class GeneralReportResponseDto {
    Integer reservationCount;
    Double profit;
    List<ReservationDto> reservations;
}
