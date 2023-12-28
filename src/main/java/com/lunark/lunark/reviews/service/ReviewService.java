package com.lunark.lunark.reviews.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.model.AccountRole;
import com.lunark.lunark.auth.service.IAccountService;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import com.lunark.lunark.reviews.model.Review;
import com.lunark.lunark.reviews.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService<Review> {
    private static final int reviewDeadline = 7;

    @Autowired
    IReviewRepository reviewRepository;

    @Autowired
    IAccountService accountService;

    @Autowired
    IPropertyRepository propertyRepository;

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    Clock clock;

    @Override
    public Collection<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> find(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review create(Review review) {
        return this.reviewRepository.saveAndFlush(review);
    }

    @Override
    public Review createPropertyReview(Review review, Long propertyId) {
        Property property = this.propertyRepository.findById(propertyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));
        property.getReviews().add(review);
        this.propertyRepository.saveAndFlush(property);
        return review;
    }

    @Override
    public Review createHostReview(Review review, Long propertyId) {
        //TODO: Create host reveiews
        return null;
    }

    @Override
    public Review update(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public boolean guestEligibleToReivew(Long guestId, Long propertyId) {
        Collection<Reservation> eligibleReservations = reservationRepository.findAllPastReservationsAtPropertyForGuestAfterDate(guestId, propertyId, LocalDate.now(clock).minusDays(reviewDeadline));
        Optional<Account> guest = accountService.find(guestId);
        if (guest.isEmpty() || guest.get().getRole() != AccountRole.GUEST || propertyRepository.findPropertyReviewByGuest(propertyId, guestId).isPresent()) {
            return false;
        }
        return !eligibleReservations.isEmpty();
    }

    public Collection<Review> getAllReviewsForHost(Long hostId) {
        Optional<Account> host = accountService.find(hostId);
        return host.map(Account::getReviews).orElse(Collections.emptyList());
    }

    public Collection<Review> getALlReviewsForProperty(Long propertyId) {
       Optional<Property> property = propertyRepository.findById(propertyId);
       return property.map(Property::getReviews).orElse(Collections.emptyList());
    }
}
