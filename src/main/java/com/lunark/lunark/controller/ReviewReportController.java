package com.lunark.lunark.controller;

import com.lunark.lunark.dto.ReviewReportRequestDto;
import com.lunark.lunark.dto.ReviewReportResponseDto;
import com.lunark.lunark.service.ReviewReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reports/reviews")
public class ReviewReportController {
    @Autowired
    private ReviewReportService reviewReportService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewReportResponseDto> getReviewReport(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new ReviewReportResponseDto(null, null, null), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewReportResponseDto>> getAll() {
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewReportResponseDto> createReviewReport(@RequestBody ReviewReportRequestDto dto) {
        return new ResponseEntity<>(new ReviewReportResponseDto(null, null, null), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewReportResponseDto> updateReviewReport(@PathVariable Long id, @RequestBody ReviewReportRequestDto dto) {
        return new ResponseEntity<>(new ReviewReportResponseDto(null, null, null), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ReviewReportResponseDto> deleteReviewReport(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
