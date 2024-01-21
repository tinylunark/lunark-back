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
        Review propertyReview = toReview(reviewRequestDto, author);
        propertyReview.setType(Review.ReviewType.PROPERTY);
        return propertyReview;
    }
    public static Review toHostReview(ReviewRequestDto reviewRequestDto, Account author) {
        Review hostReview = toReview(reviewRequestDto, author);
        hostReview.setType(Review.ReviewType.HOST);
        return hostReview;
    }

    private static Review toReview(ReviewRequestDto reviewRequestDto, Account author)  {
        Review review =  modelMapper.map(reviewRequestDto, Review.class);
        review.setDate(LocalDateTime.now(clock));
        review.setAuthor(author);
        return review;
    }
    public static ReviewDto toDto(Review review) {
        return new ReviewDto(review.getRating(),
                review.getDescription(),
                review.getDate(),
                review.getAuthor().getName() + " " + review.getAuthor().getSurname(),
                review.getAuthor().getId(),
                review.getType(),
                review.getId()
                );
    }
}
