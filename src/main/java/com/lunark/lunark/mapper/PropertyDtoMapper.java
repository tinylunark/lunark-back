package com.lunark.lunark.mapper;

import com.lunark.lunark.amenities.dto.AmenityResponseDto;
import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.properties.dto.AvailabilityEntryDto;
import com.lunark.lunark.properties.dto.PropertyRequestDto;
import com.lunark.lunark.properties.dto.PropertyResponseDto;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyDtoMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public PropertyDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Property fromDtoToProperty(PropertyRequestDto propertyRequestDto) {
        Property property = modelMapper.map(propertyRequestDto, Property.class);

        List<PropertyAvailabilityEntry> availabilityEntries = propertyRequestDto.getAvailabilityEntries().stream()
                        .map(availabilityEntryDto -> modelMapper.map(availabilityEntryDto, PropertyAvailabilityEntry.class))
                        .collect(Collectors.toList());
        property.setAvailabilityEntries(availabilityEntries);

        List<Amenity> amenities = propertyRequestDto.getAmenityIds().stream()
                .map(id -> {
                    Amenity amenity = new Amenity();
                    amenity.setId(id);
                    return amenity;
                })
                .toList();
        property.setAmenities(amenities);

        return property;
    }

    public static PropertyResponseDto fromPropertyToDto(Property property) {
        PropertyResponseDto propertyResponseDto = modelMapper.map(property, PropertyResponseDto.class);
        List<AvailabilityEntryDto> availabilityEntryDtos = property.getAvailabilityEntries().stream()
                .map(propertyAvailabilityEntry -> modelMapper.map(propertyAvailabilityEntry, AvailabilityEntryDto.class))
                .collect(Collectors.toList());
        propertyResponseDto.setAvailabilityEntries(availabilityEntryDtos);

        List<AmenityResponseDto> amenities = property.getAmenities().stream()
                .map(amenity -> modelMapper.map(amenity, AmenityResponseDto.class))
                .toList();
        propertyResponseDto.setAmenities(amenities);

        return propertyResponseDto;
    }
}
