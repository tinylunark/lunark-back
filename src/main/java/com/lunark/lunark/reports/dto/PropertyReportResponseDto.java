package com.lunark.lunark.reports.dto;

import com.lunark.lunark.reports.model.MonthlyReport;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
public class PropertyReportResponseDto {
    Collection<MonthlyReport> monthlyReports;
}
