package com.lunark.lunark.reservation.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.model.ReservationStatus;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import com.lunark.lunark.reservations.service.ReservationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
    @Mock
    private IReservationRepository reservationRepository;

    @Mock
    private IPropertyRepository propertyRepository;

    @Mock
    private IAccountRepository accountRepository;

    @InjectMocks
    private ReservationService service;

    private Property property;

    private ReservationRequestDto dto;

    @BeforeEach
    void setup() {
        List<PropertyAvailabilityEntry> entries = List.of(
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(2), 100.0, property),
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(3), 100.0, property),
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(4), 100.0, property)
        );

        property = Property.builder()
                .id(1L)
                .minGuests(3)
                .maxGuests(5)
                .availabilityEntries(entries)
                .build();
    }

    @Test
    void testNonExistentPropertyShouldThrowException() {
        dto = new ReservationRequestDto(2L, null, null, 2);
        Mockito.when(propertyRepository.findById(dto.getPropertyId())).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(dto, "user");
        });
        Assertions.assertTrue(exception.getMessage().contains("property"));
    }

    @ParameterizedTest
    @MethodSource(value = "invalidDatesSource")
    void testInvalidDateRangeShouldThrowException(ReservationRequestDto param) {
        Mockito.lenient().when(propertyRepository.findById(param.getPropertyId())).thenReturn(Optional.of(property));

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(param, "user");
        });

        Assertions.assertTrue(exception.getMessage().contains("date"));
    }

    static List<ReservationRequestDto> invalidDatesSource() {
        return List.of(
                new ReservationRequestDto(1L, LocalDate.now(), LocalDate.now().plusDays(1), 2),
                new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 2)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "invalidGuestsSource")
    void testInvalidNumberOfGuestsShouldThrowException(ReservationRequestDto param) {
        Mockito.lenient().when(propertyRepository.findById(param.getPropertyId())).thenReturn(Optional.of(property));

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(param, "user");
        });
        Assertions.assertTrue(exception.getMessage().contains("guest"));
    }

    static List<ReservationRequestDto> invalidGuestsSource() {
        return List.of(
                new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 2),
                new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 7)
        );
    }

    @Test
    void testNonExistentUserShouldThrowException() {
        dto = new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 3);

        Mockito.when(propertyRepository.findById(dto.getPropertyId())).thenReturn(Optional.of(property));
        Mockito.when(accountRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(dto, "user");
        });

        Assertions.assertTrue(exception.getMessage().contains("user"));
    }

    @Test
    void testCreatingReservationShouldCreateNewReservation() {
        dto = new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), 3);

        Mockito.when(propertyRepository.findById(dto.getPropertyId())).thenReturn(Optional.of(property));
        Account testAccount = new Account();
        testAccount.setEmail("user");
        Mockito.when(accountRepository.findByEmail("user")).thenReturn(Optional.of(testAccount));
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        Reservation reservation = service.create(dto, "user");

        Assertions.assertEquals(300.0, reservation.getPrice());
        Assertions.assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        Assertions.assertEquals(LocalDate.now().plusDays(2), reservation.getStartDate());
        Assertions.assertEquals(LocalDate.now().plusDays(4), reservation.getEndDate());
        Assertions.assertEquals("user", reservation.getGuest().getEmail());
        Assertions.assertEquals(1L, reservation.getProperty().getId());
        Assertions.assertEquals(3, reservation.getNumberOfGuests());
    }

    @Test
    public void testFindByIdSuccess() {
        Reservation mockReservation = new Reservation();
        mockReservation.setId(1L);
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(mockReservation));

        Optional<Reservation> result = service.findById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getId(), Long.valueOf(1L));
        Mockito.verify(reservationRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Reservation> result = service.findById(1L);
        assertFalse(result.isPresent());
    }
}
