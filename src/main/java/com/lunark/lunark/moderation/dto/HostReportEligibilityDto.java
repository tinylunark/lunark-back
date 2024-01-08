package com.lunark.lunark.moderation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostReportEligibilityDto {
    Long hostId;
    boolean eligible;
}
