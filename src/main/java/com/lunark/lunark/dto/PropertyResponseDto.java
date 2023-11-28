package com.lunark.lunark.dto;

import com.lunark.lunark.model.PricingMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Collection;

@Data
@NoArgsConstructor
public class PropertyResponseDto {
    Long id;
    String name;
    int minGuests;
    int maxGuests;
    String description;
    double latitude;
    double longitude;
    Collection<Image> photos;
    boolean approved;
    PricingMode pricingMode;
    int cancellationDeadline;
    boolean autoApproveEnabled;
}
