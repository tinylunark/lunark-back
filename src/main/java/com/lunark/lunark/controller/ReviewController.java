package com.lunark.lunark.controller;

import com.lunark.lunark.dto.ReviewDto;
import com.lunark.lunark.mapper.ReviewDtoMapper;
import com.lunark.lunark.model.Review;
import com.lunark.lunark.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> getReview(@PathVariable("id") Long id) {
        Optional<Review> review = reviewService.find(id);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(review.get(), HttpStatus.OK);
    }

    //reviews?type=property or ?type=host
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto,
                                               @RequestParam(value = "type", defaultValue = "property") String reviewType) {
        try {
            Review newReview = reviewService.create(reviewDto.toReview(reviewType));
            return new ResponseEntity<>(newReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable("id") Long id){
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Review> updateReview(@RequestBody ReviewDto reviewDto, @PathVariable("id") Long id) {
        Optional<Review> existingReview = reviewService.find(id);
        if(existingReview.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Review updatedReview = reviewService.update(existingReview.get());
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
