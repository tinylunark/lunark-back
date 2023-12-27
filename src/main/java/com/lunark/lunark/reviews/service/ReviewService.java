package com.lunark.lunark.reviews.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.model.AccountRole;
import com.lunark.lunark.auth.service.IAccountService;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import com.lunark.lunark.reviews.model.Review;
import com.lunark.lunark.properties.service.IPropertyService;
import com.lunark.lunark.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService<Review> {
    private static final int reviewDeadline = 7;

    @Autowired
    ReviewRepository reviewRepository;

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
