package com.lunark.lunark.properties.dto;

import com.lunark.lunark.amenities.dto.AmenityResponseDto;
import com.lunark.lunark.auth.dto.AccountDto;
import com.lunark.lunark.properties.model.Address;
import com.lunark.lunark.properties.model.PricingMode;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyImage;
import com.lunark.lunark.reviews.dto.ReviewDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

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
    List<AmenityResponseDto> amenities;
    Collection<AvailabilityEntryDto> availabilityEntries;
    List<ReviewDto> reviews;
    Property.PropertyType type;
    AccountDto host;
    Double averageRating;
}
