package com.lunark.lunark.controller;

import org.modelmapper.internal.bytebuddy.implementation.Implementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property-report")
public class PropertyReportController {

    @GetMapping(value = "/download", produces =  "application/json")
    public ResponseEntity<String> downloadRecord(@RequestParam Long propertyId, @RequestParam String startDate, @RequestParam String endDate) {
        // TODO: Implementation
       return ResponseEntity.ok("Dummy string.");
    }

    @GetMapping("/")
    public ResponseEntity<String> sendRecordData(@RequestParam Long propertyId, @RequestParam String startDate, @RequestParam String endDate) {
        String response = String.format("Record data received: PropertyId=%d, StartDate=%s, EndDate=%s",
                propertyId, startDate, endDate);
        return ResponseEntity.ok().body(response);
    }
}
