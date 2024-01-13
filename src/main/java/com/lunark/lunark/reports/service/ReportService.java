package com.lunark.lunark.reports.service;

import com.lunark.lunark.reports.model.DailyReport;
import com.lunark.lunark.reports.model.GeneralReport;
import com.lunark.lunark.reports.model.MonthlyReport;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class ReportService implements IReportService {
    private final IReservationRepository reservationRepository;

    @Autowired
    public ReportService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public GeneralReport generateGeneralReport(LocalDate start, LocalDate end, Long hostId) {
        Collection<DailyReport> dailyReports = reservationRepository.getDailyReports(start, end, hostId);
        Double totalProfit = dailyReports.stream().mapToDouble(report -> report.getProfit()).sum();
        Long totalReservationCount = dailyReports.stream().mapToLong(report -> report.getReservationCount()).sum();

        return new GeneralReport(dailyReports, totalProfit, totalReservationCount);
    }

    @Override
    public Collection<MonthlyReport> generateMonthlyReports(Integer year, Long propertyId) {
        return reservationRepository.generateMonthlyReports(propertyId, year);
    }
}
