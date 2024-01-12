package com.lunark.lunark.reports.service;

import com.lunark.lunark.reports.dto.GeneralReportResponseDto;
import com.lunark.lunark.reports.model.GeneralReport;
import com.lunark.lunark.reports.model.MonthlyReport;

import java.time.LocalDate;
import java.util.Collection;

public interface IReportService {
    GeneralReport generateGeneralReport(LocalDate start, LocalDate end, Long hostId);
    Collection<MonthlyReport> generateMonthlyReports(Integer year, Long propertyId);
}
