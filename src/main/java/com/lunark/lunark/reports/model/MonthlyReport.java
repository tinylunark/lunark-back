package com.lunark.lunark.reports.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyReport {
    Integer month;
    Double profit;
    Long reservationCount;
}
