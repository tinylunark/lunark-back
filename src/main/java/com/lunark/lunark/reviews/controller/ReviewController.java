package com.lunark.lunark.reviews.controller;

import com.lunark.lunark.reviews.dto.ReviewApprovalDto;
import com.lunark.lunark.reviews.dto.ReviewDto;
import com.lunark.lunark.reviews.model.Review;
import com.lunark.lunark.reviews.service.ReviewService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> getReview(@PathVariable("id") Long id) {
        Optional<Review> review = reviewService.find(id);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(review.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/host/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Review>> getReviewForHost(@PathVariable("id") Long id) {
        Collection<Review> reviews = reviewService.getAllReviewsForHost(id);
        if(reviews.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/property/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Review>> getReviewForProperty(@PathVariable("id") Long id) {
        Collection<Review> reviews = reviewService.getALlReviewsForProperty(id);
        if(reviews.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    //reviews?type=property&id=<property_id> or ?type=host&id=<host_id>
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto,
                                               @RequestParam(value = "type", defaultValue = "property") String reviewType,
                                               @RequestParam(value = "id") Long propertyOrHostId) {
        try {
            Review newReview = reviewService.create(reviewDto.toReview(reviewType));
            // TODO: Add review to host or property
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
