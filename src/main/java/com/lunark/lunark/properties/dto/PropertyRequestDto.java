package com.lunark.lunark.properties.dto;

import com.lunark.lunark.properties.model.Address;
import com.lunark.lunark.properties.model.PricingMode;
import com.lunark.lunark.properties.model.Property;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class PropertyRequestDto {
    Long id;
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
    Property.PropertyType type;
    Long hostId;
}
