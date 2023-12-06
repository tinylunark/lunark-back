package com.lunark.lunark.dto;

import com.lunark.lunark.model.Address;
import com.lunark.lunark.model.PricingMode;
import com.lunark.lunark.model.PropertyImage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class PropertyRequestDto {
    String name;
    int minGuests;
    int maxGuests;
    String description;
    Address address;
    double latitude;
    double longitude;
    Collection<PropertyImage> images;
    PricingMode pricingMode;
    int cancellationDeadline;
    boolean autoApproveEnabled;
    Collection<Long> amenities;
}
