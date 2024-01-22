package com.lunark.lunark.reservations.repository;

import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.model.ReservationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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

    public static List<Arguments> provideParamsForTestFindAllPendingReservationsAtPropertyThatContainDate() {
        Arguments propertyAndReservationExist = Arguments.arguments(7L, LocalDate.of(2023, 12, 25), 2);
        Arguments reservationsExistButNotForThatProperty = Arguments.arguments(9L, LocalDate.of(2023, 12, 25), 0);
        Arguments propertyExistsButNoReservation = Arguments.arguments(7L, LocalDate.of(2025, 4, 21), 0);
        Arguments nonExistentProperty = Arguments.arguments(1234L, LocalDate.of(2023, 12, 25), 0);
        Arguments dayIsAtTheBeginning = Arguments.arguments(4L, LocalDate.of(2023, 12, 20), 1);
        Arguments dayIsAtTheEnd = Arguments.arguments(4L, LocalDate.of(2024, 1, 1), 1);
        Arguments dayIsOneDayBeforeBeginning = Arguments.arguments(4L, LocalDate.of(2023, 12, 19), 0);
        Arguments dayIsOneDayAfterEnd = Arguments.arguments(4L, LocalDate.of(2024, 1, 2), 0);
        Arguments reservationsExistForPropertyButAllRejected = Arguments.arguments(5L, LocalDate.of(2023, 11, 12), 0);
        return List.of(
                propertyAndReservationExist,
                reservationsExistButNotForThatProperty,
                propertyExistsButNoReservation,
                nonExistentProperty,
                dayIsAtTheBeginning,
                dayIsAtTheEnd,
                dayIsOneDayBeforeBeginning,
                dayIsOneDayAfterEnd,
                reservationsExistForPropertyButAllRejected
        );
    }


    @ParameterizedTest
    @MethodSource("provideParamsForTestFindAllPendingReservationsAtPropertyThatContainDate")
    @Sql("classpath:find-reservations-containing-date-test-data.sql")
    public void testFindAllPendingReservationsAtPropertyThatContainDate(Long propertyId, LocalDate date, int count) {
        List<Reservation> result = reservationRepository.findAllPendingReservationsAtPropertyThatContainDate(propertyId, date);
        Assertions.assertEquals(count, result.size());
        for (Reservation reservation: result) {
            Assertions.assertEquals(propertyId, reservation.getProperty().getId());
            Assertions.assertTrue(!reservation.getStartDate().isAfter(date));
            Assertions.assertTrue(!reservation.getEndDate().isBefore(date));
            Assertions.assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        }
    }
}
