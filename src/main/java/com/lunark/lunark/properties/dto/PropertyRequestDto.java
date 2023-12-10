package com.lunark.lunark.properties.dto;

import com.lunark.lunark.properties.model.Address;
import com.lunark.lunark.properties.model.PricingMode;
import com.lunark.lunark.properties.model.Property;
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
    PricingMode pricingMode;
    int cancellationDeadline;
    boolean autoApproveEnabled;
    Collection<Long> amenities;
    Collection<AvailabilityEntryDto> availabilityEntries;
    Property.PropertyType type;
}
