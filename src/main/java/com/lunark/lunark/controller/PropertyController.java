package com.lunark.lunark.controller;

import com.lunark.lunark.dto.AmenityDto;
import com.lunark.lunark.dto.PropertyAmenityDto;
import com.lunark.lunark.dto.PropertyRequestDto;
import com.lunark.lunark.dto.PropertyResponseDto;
import com.lunark.lunark.model.Amenity;
import com.lunark.lunark.model.Property;
import com.lunark.lunark.model.PropertyAvailabilityEntry;
import com.lunark.lunark.model.PropertyType;
import com.lunark.lunark.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/properties")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyResponseDto>> getAll(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer guestNumber,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<AmenityDto> amenities,
            @RequestParam(required = false) PropertyType type,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
            ) {
        List<PropertyResponseDto> propertyDtos = propertyService.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PropertyResponseDto.class))
                .toList();

        return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponseDto> getProperty(@PathVariable("id") Long id) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PropertyResponseDto propertyDto = modelMapper.map(property.get(), PropertyResponseDto.class);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping(value = "/average/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getAverageGrade(@PathVariable("id") Long id) {
        Double averageGrade = propertyService.getAverageGrade(id);
        if (averageGrade == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }
  
    @GetMapping(value = "/{id}/pricesAndAvailability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PropertyAvailabilityEntry>> getPricesAndAvailability(@PathVariable("id") Long id) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(property.get().getAvailabilityEntries(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/pricesAndAvailability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PropertyAvailabilityEntry>> changePricesAndAvailability(@PathVariable("id") Long id, @RequestBody List<PropertyAvailabilityEntry> availabilityEntries) {
        return new ResponseEntity<>(availabilityEntries, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponseDto> createProperty(@RequestBody PropertyRequestDto propertyDto) {
        // TODO: add service calls
        PropertyResponseDto response = modelMapper.map(propertyDto, PropertyResponseDto.class);
        response.setAmenities(Arrays.asList(new PropertyAmenityDto("Wi-Fi", null), new PropertyAmenityDto("Washing machine", null)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponseDto> updateProperty(@RequestBody PropertyRequestDto propertyDto) {
        // TODO: add service calls
        return new ResponseEntity<>(new PropertyResponseDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PropertyResponseDto> deleteProperty(@PathVariable("id") Long id) {
        // TODO: add service calls
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/approve/{id}")
    public ResponseEntity<?> approveProperty(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
