package com.lunark.lunark.reviews.controller;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.mapper.ReviewDtoMapper;
import com.lunark.lunark.reviews.dto.ReviewApprovalDto;
import com.lunark.lunark.reviews.dto.ReviewDto;
import com.lunark.lunark.reviews.dto.PropertyReviewEligibilityDto;
import com.lunark.lunark.reviews.dto.ReviewRequestDto;
import com.lunark.lunark.reviews.model.Review;
import com.lunark.lunark.reviews.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Clock clock;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> getReview(@PathVariable("id") Long id) {
        Optional<Review> review = reviewService.find(id);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(review.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/host/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDto>> getReviewForHost(@PathVariable("id") Long id) {
        Collection<Review> reviews = reviewService.getAllReviewsForHost(id);
        if(reviews == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews.stream().map(ReviewDtoMapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/property/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Review>> getReviewForProperty(@PathVariable("id") Long id) {
        Collection<Review> reviews = reviewService.getALlReviewsForProperty(id);
        if(reviews.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping(value = "/property/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDto> createPropertyReview(@RequestBody ReviewRequestDto reviewDto, @PathVariable(value = "id") Long id) {
        Account guest = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!this.reviewService.guestEligibleToReivew(guest.getId(), id)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        Review review = ReviewDtoMapper.toPropertyReview(reviewDto, guest);
        review = this.reviewService.createPropertyReview(review, id);
        return new ResponseEntity<>(ReviewDtoMapper.toDto(review), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('GUEST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable("id") Long id){
        Account guest = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (reviewService.find(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!reviewService.isBy(guest, id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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

    @GetMapping(value = "property-review-eligibility/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyReviewEligibilityDto> checkEligibilityToReviewProperty(@PathVariable("id") Long id) {
        Account guest;
        try {
            guest = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            return new ResponseEntity<>(new PropertyReviewEligibilityDto(false, null, id), HttpStatus.OK);
        }
        PropertyReviewEligibilityDto propertyReviewEligibilityDto = new PropertyReviewEligibilityDto(reviewService.guestEligibleToReivew(guest.getId(), id), guest.getId(), id);
        return new ResponseEntity<>(propertyReviewEligibilityDto, HttpStatus.OK);
    }

}
