package com.lunark.lunark.reservations.repository;

import com.lunark.lunark.reservations.model.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Collection;

@DataJpaTest
public class ReviewRepositoryTest {
    @Autowired
    IReservationRepository reservationRepository;

    @Test
    public void shouldReturnAcceptedReservationWhenLookingForPastAcceptedReservations() {
        Collection<Reservation> reservations = reservationRepository.findAllPastReservationsAtPropertyForGuestAfterDate(1L, 4L, LocalDate.of(2023, 12, 25));
        Assertions.assertEquals(1, reservations.size());
    }

    @Test
    public void shouldNotReturnCancelledReservationWhenLookingForPastAcceptedReservations() {
        Collection<Reservation> reservations = reservationRepository.findAllPastReservationsAtPropertyForGuestAfterDate(1L, 7L, LocalDate.of(2023, 12, 25));
        Assertions.assertEquals(0, reservations.size());
    }

    @Test
    public void shouldNotReturnReservationThatIsTooOldWhenLookingForPastAcceptedReservations() {
        Collection<Reservation> reservations = reservationRepository.findAllPastReservationsAtPropertyForGuestAfterDate(1L, 5L, LocalDate.of(2023, 12, 25));
        Assertions.assertEquals(0, reservations.size());
    }
}
