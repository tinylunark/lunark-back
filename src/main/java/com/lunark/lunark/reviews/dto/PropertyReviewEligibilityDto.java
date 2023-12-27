package com.lunark.lunark.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyReviewEligibilityDto {
    private boolean eligible;
    private Long guestId;
    private Long propertyId;
}
