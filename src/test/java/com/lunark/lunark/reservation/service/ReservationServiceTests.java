package com.lunark.lunark.reservation.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.notifications.service.INotificationService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
    @Mock
    private IReservationRepository reservationRepository;

    @Mock
    private IPropertyRepository propertyRepository;

    @Mock
    private IAccountRepository accountRepository;
    @Mock
    private INotificationService notificationService;

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
    void Non_existent_property_should_throw_exception() {
        dto = new ReservationRequestDto(2L, null, null, 2);

        Mockito.when(propertyRepository.findById(dto.getPropertyId())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(dto, "user");
        });

        Assertions.assertTrue(exception.getMessage().contains("property"));
        Mockito.verify(propertyRepository, Mockito.times(1)).findById(2L);
        Mockito.verifyNoInteractions(accountRepository, notificationService, reservationRepository);
    }

    @ParameterizedTest
    @MethodSource(value = "invalidDatesSource")
    void Invalid_date_range_should_throw_exception(ReservationRequestDto param) {
        Mockito.lenient().when(propertyRepository.findById(param.getPropertyId())).thenReturn(Optional.of(property));

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(param, "user");
        });

        Assertions.assertTrue(exception.getMessage().contains("date"));
        Mockito.verify(propertyRepository, Mockito.times(1)).findById(param.getPropertyId());
        Mockito.verifyNoInteractions(accountRepository, notificationService, reservationRepository);
    }

    @ParameterizedTest
    @MethodSource(value = "invalidGuestsSource")
    void Invalid_number_of_guests_should_throw_exception(ReservationRequestDto param) {
        Mockito.lenient().when(propertyRepository.findById(param.getPropertyId())).thenReturn(Optional.of(property));

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(param, "user");
        });

        Assertions.assertTrue(exception.getMessage().contains("guest"));
        Mockito.verify(propertyRepository, Mockito.times(1)).findById(param.getPropertyId());
        Mockito.verifyNoInteractions(accountRepository, notificationService, reservationRepository);
    }

    @Test
    void Non_existent_user_should_throw_exception() {
        dto = new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 3);

        Mockito.when(propertyRepository.findById(dto.getPropertyId())).thenReturn(Optional.of(property));
        Mockito.when(accountRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(dto, "user");
        });

        Assertions.assertTrue(exception.getMessage().contains("user"));
        Mockito.verify(propertyRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).findByEmail("user");
        Mockito.verifyNoInteractions(notificationService, reservationRepository);
    }

    @Test
    void Creating_reservation_should_create_new_reservation() {
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
        Mockito.verify(propertyRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).findByEmail("user");
        Mockito.verify(notificationService, Mockito.times(1)).createNotification(Mockito.any(Reservation.class));
        Mockito.verify(reservationRepository, Mockito.times(1)).save(Mockito.any(Reservation.class));
    }

    static List<ReservationRequestDto> invalidDatesSource() {
        return List.of(
                new ReservationRequestDto(1L, LocalDate.now(), LocalDate.now().plusDays(1), 2),
                new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 2)
        );
    }

    static List<ReservationRequestDto> invalidGuestsSource() {
        return List.of(
                new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 2),
                new ReservationRequestDto(1L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 7)
        );
    }

    @Test
    public void testRejectAllPendingReservationsAtPropertyThatContainDate() {
        Long propertyId = 4L;
        LocalDate date = LocalDate.of(2023, 4, 21);
        Account account = new Account();
        account.setId(1L);
        Property property = new Property();
        property.setId(1L);
        property.setName("Test property");
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 24), 1, ReservationStatus.PENDING, 1000.0, property, account);
        Mockito.when(reservationRepository.findAllPendingReservationsAtPropertyThatContainDate(propertyId, date)).thenReturn(
                new ArrayList<>(List.of(reservation))
        );
        Mockito.when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        service.rejectAllPendingReservationsAtPropertyThatContainDate(propertyId, date);
        Assertions.assertEquals(ReservationStatus.REJECTED, reservation.getStatus());

        Mockito.verify(reservationRepository).findAllPendingReservationsAtPropertyThatContainDate(propertyId, date);
        Mockito.verify(reservationRepository).findById(reservation.getId());
        Mockito.verify(reservationRepository).saveAndFlush(reservation);
        Mockito.verifyNoMoreInteractions(reservationRepository);
        Mockito.verify(notificationService).createNotification(reservation);
        Mockito.verifyNoMoreInteractions(notificationService);
    }
}
