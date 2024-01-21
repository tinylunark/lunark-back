package com.lunark.lunark.properties.dto;

import com.lunark.lunark.properties.model.Property;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PropertySearchDto {
    String address;
    Integer guestNumber;
    LocalDate startDate;
    LocalDate endDate;
    Boolean approved;
    List<Long> amenityIds;
    Property.PropertyType type;
    Double minPrice;
    Double maxPrice;
    String sort;
}
