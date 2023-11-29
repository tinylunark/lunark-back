package com.lunark.lunark.dto;

import com.lunark.lunark.model.PricingMode;
import com.lunark.lunark.model.Property;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Collection;
import java.util.stream.Collectors;

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
    Collection<AmenityDto> amenities;
}
