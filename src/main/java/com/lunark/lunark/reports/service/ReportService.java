package com.lunark.lunark.reports.service;

import com.lunark.lunark.reports.dto.GeneralReportResponseDto;
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
    public GeneralReportResponseDto generateGeneralReport(LocalDate start, LocalDate end, Long hostId) {
        return null;
    }

    @Override
    public Collection<MonthlyReport> generateMonthlyReports(Integer year, Long propertyId) {
        return null;
    }
}
