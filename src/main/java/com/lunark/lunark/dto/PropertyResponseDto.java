package com.lunark.lunark.dto;

import com.lunark.lunark.model.Address;
import com.lunark.lunark.model.PricingMode;
import com.lunark.lunark.model.PropertyImage;
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
    Address address;
    double latitude;
    double longitude;
    Collection<PropertyImage> images;
    boolean approved;
    PricingMode pricingMode;
    int cancellationDeadline;
    boolean autoApproveEnabled;
    Collection<PropertyAmenityDto> amenities;
    Collection<AvailabilityEntryDto> availabilityEntries;
}
