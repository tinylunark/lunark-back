package com.lunark.lunark.controller;

import com.lunark.lunark.dto.PropertyReportDto;
import org.modelmapper.internal.bytebuddy.implementation.Implementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/property-report")
public class PropertyReportController {

    @GetMapping(value = "/download", produces =  "application/json")
    public ResponseEntity<String> downloadRecord(@RequestParam Long propertyId, @RequestParam String startDate, @RequestParam String endDate) {
        // TODO: Implementation

       return ResponseEntity.ok("Dummy pdf.");
    }

    @GetMapping("/")
    public ResponseEntity<PropertyReportDto> sendRecordData(@RequestParam Long propertyId, @RequestParam String startDate, @RequestParam String endDate) {
        PropertyReportDto propertyReportDto = new PropertyReportDto(
                120000.0,
                120,
                Arrays.asList(10000.0, 10000.0, 10000.0, 10000.0, 10000.0, 10000.0, 10000.0, 10000.0, 10000.0, 10000.0, 10000.0, 10000.0),
                Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10)
        );
        return new ResponseEntity<>(propertyReportDto, HttpStatus.OK);
    }
}
