package com.lunark.lunark.reservations.repository;

import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.reservations.model.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Collection;

@DataJpaTest
@ActiveProfiles("test")
@Sql("classpath:past-reservation-test-data.sql")
public class ReservationRepositoryTest {
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

    @ParameterizedTest
    @CsvSource(value = {
            "1, 10, 2",
            "4, 222, 0",
            "222, 2, 0",
            "1, 2, 0",
            "4, 2, 0",
            "4, 10, 0",
    })
    public void testFindAllPastReservationsAtHostAfterDate(Long guestId, Long hostId, int numberOfReservations) {
        Collection<Reservation> reservations = reservationRepository.findAllPastReservationsAtHostAfterDate(guestId, hostId, LocalDate.of(2023, 12, 25));
        Assertions.assertEquals(numberOfReservations, reservations.size());
    }
}
