package com.lunark.lunark.controller;

import com.lunark.lunark.dto.PropertyDto;
import com.lunark.lunark.model.Property;
import com.lunark.lunark.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<PropertyDto>> getAll() {
        List<PropertyDto> propertyDtos = propertyService.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PropertyDto.class))
                .toList();

        return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyDto> getProperty(@PathVariable("id") Long id) {
        Optional<Property> property = propertyService.find(id);

        if (property.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PropertyDto propertyDto = modelMapper.map(property, PropertyDto.class);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping(value = "/average/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getAverageGrade(@PathVariable("id") Long id) {
        Double averageGrade = propertyService.getAverageGrade(id);
        if (averageGrade < 0 ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyDto propertyDto) {
        // TODO: add service calls
        return new ResponseEntity<>(new PropertyDto(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyDto> updateProperty(@RequestBody PropertyDto propertyDto) {
        // TODO: add service calls
        return new ResponseEntity<>(new PropertyDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PropertyDto> deleteProperty(@PathVariable("id") Long id) {
        // TODO: add service calls
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
