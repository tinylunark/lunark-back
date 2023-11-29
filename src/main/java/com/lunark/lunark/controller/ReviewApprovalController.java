package com.lunark.lunark.controller;

import com.lunark.lunark.dto.ReviewApprovalDto;
import com.lunark.lunark.model.Review;
import com.lunark.lunark.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewApprovalController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewApprovalDto> approveReview(@PathVariable("id") Long id) {
        Optional<Review> existingReview = reviewService.find(id);
        if(existingReview.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Review reviewToApprove = existingReview.get();
            reviewToApprove.setApproved(true);
            Review approvedReview = reviewService.update(existingReview.get());
            return new ResponseEntity<>(modelMapper.map(approvedReview, ReviewApprovalDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
