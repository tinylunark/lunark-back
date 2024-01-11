package com.lunark.lunark.reports.dto;

import lombok.Data;

import java.time.Year;

@Data
public class PropertyReportRequestDto {
    Long propertyId;
    Integer year;
}
