package com.lunark.lunark.properties.controller;

import com.lunark.lunark.mapper.PropertyDtoMapper;
import com.lunark.lunark.properties.dto.AvailabilityEntryDto;
import com.lunark.lunark.properties.dto.PropertyRequestDto;
import com.lunark.lunark.properties.dto.PropertyResponseDto;
import com.lunark.lunark.properties.dto.PropertySearchDto;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.properties.model.PropertyImage;
import com.lunark.lunark.properties.service.IPropertyService;
import com.lunark.lunark.validation.ValidPropertySearchDates;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/properties")
@Validated
public class PropertyController {
    private final IPropertyService propertyService;
    private final ModelMapper modelMapper;

    @Autowired
    private PropertyDtoMapper propertyDtoMapper;

    @Autowired
    public PropertyController(IPropertyService propertyService, ModelMapper modelMapper) {
        this.propertyService = propertyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidPropertySearchDates
    public ResponseEntity<List<PropertyResponseDto>> getAll(
            @RequestParam(required = false) @Pattern(message = "Location can not contain special characters", regexp = "^[^%<>$]*$") String location,
            @RequestParam(required = false) @Positive(message = "Guest number must be positive") Integer guestNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Future(message = "Start date must be in the future") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Future(message = "End date must be in the future") LocalDate endDate,
            @RequestParam(required = false) List<@PositiveOrZero Long> amenityIds,
            @RequestParam(required = false) Property.PropertyType type,
            @RequestParam(required = false) @Positive(message = "Min price must be positive") Double minPrice,
            @RequestParam(required = false) @Positive(message = "Max price must be positive") Double maxPrice
    ) {

        PropertySearchDto filter = PropertySearchDto.builder()
                .address(location)
                .guestNumber(guestNumber)
                .startDate(startDate)
                .endDate(endDate)
                .approved(true)
                .type(type)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .amenityIds(amenityIds)
                .build();

        List<Property> properties = propertyService.findByFilter(filter);

        List<PropertyResponseDto> propertyDtos = properties.stream()
                .map(PropertyDtoMapper::fromPropertyToDto)
                .toList();
        return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
    }

    @GetMapping(value="/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PropertyResponseDto>> getAllUnapproved(SpringDataWebProperties pageable) {
        List<Property> unapprovedProperties = propertyService.findUnapproved();
        List<PropertyResponseDto> propertyDtos = unapprovedProperties.stream() .map(PropertyDtoMapper::fromPropertyToDto) .toList();
        return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
    }

    @GetMapping(value="/my-properties", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<List<PropertyResponseDto>> getMyProperties(@RequestParam("hostId") @NotNull @PositiveOrZero Long hostId, SpringDataWebProperties pageable) {
        List<Property> myProperties = propertyService.findAllPropertiesForHost(hostId);
        List<PropertyResponseDto> propertyDtos = myProperties.stream() .map(PropertyDtoMapper::fromPropertyToDto) .toList();
        return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponseDto> getProperty(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PropertyResponseDto propertyDto = PropertyDtoMapper.fromPropertyToDto(property.get());
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping(value = "/average/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getAverageGrade(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        Double averageGrade = propertyService.getAverageGrade(id);
        if (averageGrade == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }
  
    @GetMapping(value = "/{id}/pricesAndAvailability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AvailabilityEntryDto>> getPricesAndAvailability(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
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
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<Collection<AvailabilityEntryDto>> changePricesAndAvailability(@PathVariable("id") @NotNull @PositiveOrZero Long id, @RequestBody @Valid List<AvailabilityEntryDto> availabilityEntries) {
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
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<PropertyResponseDto> createProperty(@Valid @RequestBody PropertyRequestDto propertyDto) {
        Property property = propertyService.create(propertyDtoMapper.fromDtoToProperty(propertyDto));
        PropertyResponseDto response = PropertyDtoMapper.fromPropertyToDto(property);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<PropertyResponseDto> updateProperty(@RequestBody @Valid PropertyRequestDto propertyDto) {
        Property property = propertyDtoMapper.fromDtoToProperty(propertyDto);
        if (this.propertyService.find(property.getId()).isEmpty())  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        property = this.propertyService.update(property, propertyDto.getId());
        property = this.propertyService.deleteImages(property.getId());
        return new ResponseEntity<>(PropertyDtoMapper.fromPropertyToDto(property), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<PropertyResponseDto> deleteProperty(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        // TODO: add service calls
        if (this.propertyService.find(id).isEmpty())  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.propertyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Property> approveProperty(@PathVariable @NotNull @PositiveOrZero Long id) {
        return propertyService.find(id)
                .map(this::approveAndSaveProperty)
                .orElse(notFoundResponse());
    }

    private ResponseEntity<Property> approveAndSaveProperty(Property property) {
        property.setApproved(true);
        propertyService.update(property, property.getId());
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<Property> notFoundResponse() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{propertyId}/images/{imageId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("propertyId") @NotNull @PositiveOrZero Long propertyId, @PathVariable("imageId") @NotNull @PositiveOrZero Long imageId) {
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
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<?> uploadImage(@PathVariable("id") @NotNull @PositiveOrZero Long id, @RequestParam("image") MultipartFile file) {
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
