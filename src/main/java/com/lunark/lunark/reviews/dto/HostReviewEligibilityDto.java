package com.lunark.lunark.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostReviewEligibilityDto {
    private boolean eligible;
    private Long guestId;
    private Long hostId;
}
