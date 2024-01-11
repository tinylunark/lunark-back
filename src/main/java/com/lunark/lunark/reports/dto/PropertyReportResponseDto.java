package com.lunark.lunark.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyReportResponseDto {
    Integer month;
    Double profit;
    Long reservationCount;
}
