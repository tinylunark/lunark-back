package com.lunark.lunark.properties.dto;

import com.lunark.lunark.properties.model.Address;
import com.lunark.lunark.properties.model.Property;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PropertySearchDto {
    Address address;
    int guestNumber;
    LocalDate startDate;
    LocalDate endDate;
    Set<Long> amenityIds;
    Property.PropertyType type;
    double minPrice;
    double maxPrice;
}
