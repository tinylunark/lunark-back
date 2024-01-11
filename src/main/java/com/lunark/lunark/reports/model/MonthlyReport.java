package com.lunark.lunark.reports.model;

import lombok.Data;

@Data
public class MonthlyReport {
    Integer month;
    Double profit;
    Long reservationCount;
}
