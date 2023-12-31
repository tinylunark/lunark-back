package com.lunark.lunark.reviews.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.service.IAccountService;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reviews.model.Review;
import com.lunark.lunark.properties.service.IPropertyService;
import com.lunark.lunark.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService<Review> {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    IAccountService accountService;

    @Autowired
    IPropertyService propertyService;

    @Override
    public Collection<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> find(Long id) {
        return reviewRepository.find(id);
    }

    @Override
    public Review create(Review review) {
        return reviewRepository.create(review);
    }

    @Override
    public Review update(Review review) {
        return reviewRepository.update(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.delete(id);
    }

    public Collection<Review> getAllReviewsForHost(Long hostId) {
        Optional<Account> host = accountService.find(hostId);
        return host.map(Account::getReviews).orElse(Collections.emptyList());
    }

    public Collection<Review> getALlReviewsForProperty(Long propertyId) {
       Optional<Property> property = propertyService.find(propertyId);
       return property.map(Property::getReviews).orElse(Collections.emptyList());
    }
}
