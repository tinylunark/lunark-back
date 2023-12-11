package com.lunark.lunark.properties.controller;

import com.lunark.lunark.amenities.dto.AmenityResponseDto;
import com.lunark.lunark.properties.dto.AvailabilityEntryDto;
import com.lunark.lunark.properties.dto.PropertyRequestDto;
import com.lunark.lunark.properties.dto.PropertyResponseDto;
import com.lunark.lunark.mapper.PropertyDtoMapper;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.properties.model.PropertyImage;
import com.lunark.lunark.properties.service.IPropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/properties")
public class PropertyController {
    private final IPropertyService propertyService;
    private final ModelMapper modelMapper;

    @Autowired
    public PropertyController(IPropertyService propertyService, ModelMapper modelMapper) {
        this.propertyService = propertyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyResponseDto>> getAll(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer guestNumber,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<AmenityResponseDto> amenities,
            @RequestParam(required = false) Property.PropertyType type,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
            ) {
        List<PropertyResponseDto> propertyDtos = propertyService.findAll()
                .stream()
                .map(p -> PropertyDtoMapper.fromPropertyToDto(p))
                .toList();

        return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponseDto> getProperty(@PathVariable("id") Long id) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PropertyResponseDto propertyDto = PropertyDtoMapper.fromPropertyToDto(property.get());
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping(value = "/average/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getAverageGrade(@PathVariable("id") Long id) {
        Double averageGrade = propertyService.getAverageGrade(id);
        if (averageGrade == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }
  
    @GetMapping(value = "/{id}/pricesAndAvailability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AvailabilityEntryDto>> getPricesAndAvailability(@PathVariable("id") Long id) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<AvailabilityEntryDto> availabilityEntryDtos = property.get().getAvailabilityEntries().stream()
                .map(propertyAvailabilityEntry -> modelMapper.map(propertyAvailabilityEntry, AvailabilityEntryDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(availabilityEntryDtos, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/pricesAndAvailability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AvailabilityEntryDto>> changePricesAndAvailability(@PathVariable("id") Long id, @RequestBody List<AvailabilityEntryDto> availabilityEntries) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PropertyAvailabilityEntry> propertyAvailabilityEntries = availabilityEntries.stream()
                .map(availabilityEntryDto -> modelMapper.map(availabilityEntryDto, PropertyAvailabilityEntry.class))
                .collect(Collectors.toList());

        if(!this.propertyService.changePricesAndAvailability(id, propertyAvailabilityEntries)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(availabilityEntries, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponseDto> createProperty(@RequestBody PropertyRequestDto propertyDto) {
        Property property = propertyService.create(PropertyDtoMapper.fromDtoToProperty(propertyDto));
        PropertyResponseDto response = PropertyDtoMapper.fromPropertyToDto(property);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponseDto> updateProperty(@RequestBody PropertyRequestDto propertyDto) {
        Property property = PropertyDtoMapper.fromDtoToProperty(propertyDto);
        if (this.propertyService.find(property.getId()).isEmpty())  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        property = this.propertyService.update(property);
        return new ResponseEntity<>(PropertyDtoMapper.fromPropertyToDto(property), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PropertyResponseDto> deleteProperty(@PathVariable("id") Long id) {
        // TODO: add service calls
        if (this.propertyService.find(id).isEmpty())  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.propertyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/approve/{id}")
    public ResponseEntity<?> approveProperty(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{propertyId}/images/{imageId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("propertyId") Long propertyId, @PathVariable("imageId") Long imageId) {
        Optional<Property> property = propertyService.find(propertyId);
        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<PropertyImage> image = propertyService.getImage(imageId, propertyId);
        if (image.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.get().getMimeType()))
                .body(image.get().getImageData());
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            propertyService.saveImage(property.get(), file);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
