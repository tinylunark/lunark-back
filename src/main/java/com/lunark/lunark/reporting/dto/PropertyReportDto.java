package com.lunark.lunark.reporting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyReportDto {
    private Double totalProfit;
    private Integer totalReservations;
    private List<Double> profitByMonth;
    private List<Integer> reservationsByMonth;
}
