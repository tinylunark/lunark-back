package com.lunark.lunark.reports.dto;

import com.lunark.lunark.reports.model.DailyReport;
import com.lunark.lunark.reports.model.GeneralReport;
import com.lunark.lunark.reservations.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
public class GeneralReportResponseDto {
    Collection<DailyReport> dailyReports;
    Double totalProfit;
    Long totalReservationCount;
}
