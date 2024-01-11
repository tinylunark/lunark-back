package com.lunark.lunark.reports.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GeneralReportRequestDto {
    LocalDate start;
    LocalDate end;
}
