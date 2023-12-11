package com.lunark.lunark.properties.dto;

import com.lunark.lunark.properties.model.Address;
import com.lunark.lunark.properties.model.PricingMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

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
    List<Long> amenityIds;
    Collection<AvailabilityEntryDto> availabilityEntries;
}
