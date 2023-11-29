package com.lunark.lunark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    @GetMapping("/download")
    public ResponseEntity<String> downloadRecord(@RequestParam Long recordId) {
       return new ResponseEntity<>("Dummy string", HttpStatus.OK);
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendRecordData(@RequestParam Long propertyId, @RequestParam String startDate, @RequestParam String endDate) {
        String response = String.format("Record data received: PropertyId=%d, StartDate=%s, EndDate=%s",
                propertyId, startDate, endDate);
        return ResponseEntity.ok().body(response);
    }
}
