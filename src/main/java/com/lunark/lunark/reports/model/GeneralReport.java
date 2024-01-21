package com.lunark.lunark.reports.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class GeneralReport {
    Collection<DailyReport> dailyReports;
    Double totalProfit;
    Long totalReservationCount;
}
