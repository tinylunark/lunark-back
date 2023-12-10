package com.lunark.lunark.amenities.controller;

import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.amenities.dto.AmenityDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {
    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AmenityDto>> getAmenities() {
        List<Amenity> amenities = Arrays.asList(
                new Amenity(1L, "Wi-Fi", null),
                new Amenity(2L, "Washing machine", null),
                new Amenity(3L, "Indoor swimming pool", null),
                new Amenity(4L, "Outdoor swimming pool", null)
        );

        Collection<AmenityDto> amenityDtos = amenities
                .stream()
                .map(amenity -> modelMapper
                .map(amenity, AmenityDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(amenityDtos, HttpStatus.OK);
    }

}
