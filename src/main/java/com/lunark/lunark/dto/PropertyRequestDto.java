package com.lunark.lunark.dto;

import com.lunark.lunark.model.Address;
import com.lunark.lunark.model.PricingMode;
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
}
