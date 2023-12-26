package com.lunark.lunark.mapper;

import com.lunark.lunark.reviews.dto.ReviewDto;
import com.lunark.lunark.reviews.model.Review;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewDtoMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public ReviewDtoMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public static Review toEntity(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }
    public static ReviewDto toDto(Review review) {
        return new ReviewDto(review.getRating(), review.getDescription(), review.getDate(), review.getAuthor().getName() + " " + review.getAuthor().getSurname(), review.getAuthor().getId(), review.getId());
    }
}
