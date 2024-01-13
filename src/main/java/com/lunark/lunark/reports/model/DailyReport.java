package com.lunark.lunark.reports.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyReport {
    LocalDate date;
    Double profit;
    Long reservationCount;
}
