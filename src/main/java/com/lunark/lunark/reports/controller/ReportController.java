package com.lunark.lunark.reports.controller;

import com.lunark.lunark.reports.dto.PropertyReportRequestDto;
import com.lunark.lunark.reports.dto.PropertyReportResponseDto;
import com.lunark.lunark.reports.model.MonthlyReport;
import com.lunark.lunark.reports.service.IReportService;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("api/reports")
public class ReportController {
    private final IReportService reportService;

    @Autowired
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/property")
    public ResponseEntity<PropertyReportResponseDto> generatePropertyReport(@RequestBody PropertyReportRequestDto dto) {
        Collection<MonthlyReport> result = reportService.generateMonthlyReports(dto.getYear(), dto.getPropertyId());

        return new ResponseEntity<>(new PropertyReportResponseDto(result), HttpStatus.OK);
    }
}
