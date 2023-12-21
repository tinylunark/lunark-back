package com.lunark.lunark.amenities.controller;

import com.lunark.lunark.amenities.dto.AmenityRequestDto;
import com.lunark.lunark.amenities.dto.AmenityResponseDto;
import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.amenities.service.IAmenityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {
    private final IAmenityService amenityService;
    private final ModelMapper modelMapper;

    @Autowired
    public AmenityController(IAmenityService amenityService, ModelMapper modelMapper) {
        this.amenityService = amenityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmenityResponseDto>> findAll() {
        List<Amenity> amenities = amenityService.findAll();

        List<AmenityResponseDto> amenityDtos = amenities.stream()
                .map(amenity -> modelMapper.map(amenity, AmenityResponseDto.class))
                .toList();

        return new ResponseEntity<>(amenityDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityResponseDto> create(@RequestBody AmenityRequestDto requestDto) {
        Amenity amenity = modelMapper.map(requestDto, Amenity.class);
        Amenity savedAmenity = amenityService.create(amenity);
        AmenityResponseDto responseDto = modelMapper.map(savedAmenity, AmenityResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityResponseDto> findById(@PathVariable("id") Long id) {
        Optional<Amenity> amenity = amenityService.findById(id);

        if (amenity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AmenityResponseDto responseDto = modelMapper.map(amenity.get(), AmenityResponseDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityResponseDto> update(@PathVariable("id") Long id, @RequestBody AmenityRequestDto requestDto) {
        Amenity amenity = modelMapper.map(requestDto, Amenity.class);
        amenity.setId(id);

        Amenity updatedAmenity = amenityService.update(amenity);
        AmenityResponseDto responseDto = modelMapper.map(updatedAmenity, AmenityResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Amenity> amenity = amenityService.findById(id);

        return amenity.map(a -> {
            amenityService.delete(a.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
