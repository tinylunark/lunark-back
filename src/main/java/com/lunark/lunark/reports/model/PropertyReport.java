package com.lunark.lunark.reports.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PropertyReport {
    Collection<MonthlyReport> monthlyReports;
}
