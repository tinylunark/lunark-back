package com.lunark.lunark.mapper;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reviews.dto.ReviewDto;
import com.lunark.lunark.reviews.dto.ReviewRequestDto;
import com.lunark.lunark.reviews.model.Review;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class ReviewDtoMapper {

    private static ModelMapper modelMapper;

    private static Clock clock;
    @Autowired
    public ReviewDtoMapper(ModelMapper modelMapper, Clock clock){
        this.modelMapper = modelMapper;
        this.clock = clock;
    }

    public static Review toEntity(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }
    public static Review toPropertyReview(ReviewRequestDto reviewRequestDto, Account author) {
        Review propertyReview =  modelMapper.map(reviewRequestDto, Review.class);
        propertyReview.setDate(LocalDateTime.now(clock));
        propertyReview.setType(Review.ReviewType.PROPERTY);
        propertyReview.setAuthor(author);
        return propertyReview;
    }
    public static ReviewDto toDto(Review review) {
        return new ReviewDto(review.getRating(), review.getDescription(), review.getDate(), review.getAuthor().getName() + " " + review.getAuthor().getSurname(), review.getAuthor().getId(), review.getId());
    }
}
