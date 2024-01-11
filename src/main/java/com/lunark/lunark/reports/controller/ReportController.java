package com.lunark.lunark.reports.controller;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reports.dto.GeneralReportResponseDto;
import com.lunark.lunark.reports.dto.PropertyReportRequestDto;
import com.lunark.lunark.reports.dto.PropertyReportResponseDto;
import com.lunark.lunark.reports.model.GeneralReport;
import com.lunark.lunark.reports.model.MonthlyReport;
import com.lunark.lunark.reports.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("api/reports")
public class ReportController {
    private final IReportService reportService;

    @Autowired
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/general")
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<GeneralReportResponseDto> generateGeneralReport(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end
            ) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        GeneralReport result = reportService.generateGeneralReport(start, end, account.getId());

        return new ResponseEntity<>(new GeneralReportResponseDto(result.getReservationCount(), result.getProfit()), HttpStatus.OK);
    }

    @GetMapping("/property")
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<PropertyReportResponseDto> generatePropertyReport(
            @RequestParam Integer year,
            @RequestParam Long propertyId
    ) {
        Collection<MonthlyReport> result = reportService.generateMonthlyReports(year, propertyId);

        return new ResponseEntity<>(new PropertyReportResponseDto(result), HttpStatus.OK);
    }
}
