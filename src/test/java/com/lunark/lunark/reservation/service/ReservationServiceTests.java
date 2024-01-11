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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountLockedException;
import java.time.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    @Mock
    private Clock clock;

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
        assertTrue(exception.getMessage().contains("property"));
    }

    @ParameterizedTest
    @MethodSource(value = "invalidDatesSource")
    void testInvalidDateRangeShouldThrowException(ReservationRequestDto param) {
        Mockito.lenient().when(propertyRepository.findById(param.getPropertyId())).thenReturn(Optional.of(property));

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.create(param, "user");
        });

        assertTrue(exception.getMessage().contains("date"));
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
        assertTrue(exception.getMessage().contains("guest"));
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

        assertTrue(exception.getMessage().contains("user"));
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

        assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getId(), Long.valueOf(1L));
        Mockito.verify(reservationRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Reservation> result = service.findById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllReservationsForPropertiesListEmptyPropertiesList() {
        List<Property> propertiesList = new ArrayList<>();
        List<Reservation> result = service.getAllReservationsForPropertiesList(propertiesList);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllReservationsForPropertiesListWithNoReservations() {
        Property property1 = new Property();
        Property property2 = new Property();
        List<Property> propertiesList = List.of(property1, property2);

        Mockito.when(reservationRepository.findAll()).thenReturn(new ArrayList<>());

        List<Reservation> result = service.getAllReservationsForPropertiesList(propertiesList);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(reservationRepository).findAll();
    }

    @Test
    void testGetAllReservationsForPropertiesListWithReservations() {
        Property property1 = new Property();
        property1.setId(1L);

        Property property2 = new Property();
        property2.setId(2L);

        Reservation reservation1 = new Reservation();
        reservation1.setProperty(property1);

        Reservation reservation2 = new Reservation();
        reservation2.setProperty(property2);

        Mockito.when(reservationRepository.findAll()).thenReturn(List.of(reservation1, reservation2));
        List<Property> propertiesList = List.of(property1, property2);

        List<Reservation> result = service.getAllReservationsForPropertiesList(propertiesList);

        assertEquals(2, result.size());
        assertTrue(result.contains(reservation1));
        assertTrue(result.contains(reservation2));
        Mockito.verify(reservationRepository).findAll();
    }

    @Test
    void testGetAllReservationsForPropertiesListWithNullProperty() {
        Property property1 = new Property();
        property1.setId(1L);

        Reservation reservation1 = new Reservation();
        reservation1.setProperty(null);
        Reservation reservation2 = new Reservation();
        reservation1.setProperty(property1);

        List<Property> propertiesList = List.of(property1);
        List<Reservation> reservations = List.of(reservation1, reservation2);
        Mockito.when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> result = service.getAllReservationsForPropertiesList(propertiesList);

        Assertions.assertEquals(result.size(), 1);
        Mockito.verify(reservationRepository).findAll();
    }

    @Test
    void testNoReservationsForPropertiesList() {
        Property property1 = new Property();
        property1.setId(1L);

        Property property2 = new Property();
        property2.setId(2L);

        Reservation reservation1 = new Reservation();
        reservation1.setProperty(property1);

        List<Property> propertiesList = List.of(property2);
        List<Reservation> reservations = List.of(reservation1);

        Mockito.when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> result = service.getAllReservationsForPropertiesList(propertiesList);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllReservationsForUserSuccess() {
        Long userId = 1L;
        Account mockAccount = new Account();
        Reservation reservation1 = Reservation.builder().id(1L).build();
        Reservation reservation2 = Reservation.builder().id(2L).build();
        mockAccount.setReservations(Set.of(reservation1, reservation2));
        when(accountRepository.findById(userId)).thenReturn(Optional.of(mockAccount));

        List<Reservation> result = service.getAllReservationsForUser(userId);

        assertEquals(2, result.size());
        assertTrue(result.contains(reservation1));
        assertTrue(result.contains(reservation2));
    }

    @Test
    void testGetAllReservationsForUserWhenUserNotFound() {
        Long userId = 1L;
        Mockito.when(accountRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.getAllReservationsForUser(userId),
                "Expected ResponseStatusException to be thrown"
        );

        assertEquals("404 NOT_FOUND \"Guest not found\"", exception.getMessage());
        assertTrue(exception.getMessage().contains("Guest not found"), "Expected message to contain 'Guest not found'");
    }

    @Test
    void testGetAllReservationsForUserWithNoReservations() {
        Long userId = 1L;
        Account mockAccount = new Account();
        mockAccount.setId(userId);
        mockAccount.setReservations(new HashSet<>());
        Mockito.when(accountRepository.findById(userId)).thenReturn(Optional.of(mockAccount));

        List<Reservation> result = service.getAllReservationsForUser(userId);

        assertTrue(result.isEmpty());
        Mockito.verify(accountRepository).findById(userId);
    }

    @Test
    void testGetIncomingReservationsForHostIdSuccess() {
        Long hostId = 1L;
        Property property = new Property();
        property.setId(1L);
        Account host = new Account();
        host.setId(hostId);
        property.setHost(host);
        Reservation reservation = new Reservation();
        reservation.setProperty(property);

        when(propertyRepository.findAll()).thenReturn(Collections.singletonList(property));
        when(reservationRepository.findByPropertyId(property.getId())).thenReturn(Collections.singletonList(reservation));

        List<Reservation> result = service.getIncomingReservationsForHostId(hostId);

        assertEquals(1, result.size());
        assertEquals(reservation, result.get(0));
        verify(propertyRepository).findAll();
        verify(reservationRepository).findByPropertyId(property.getId());
    }

    @Test
    void testGetIncomingReservationsForHostIdNoProperties() {
        Long hostId = 1L;
        when(propertyRepository.findAll()).thenReturn(Collections.emptyList());

        List<Reservation> result = service.getIncomingReservationsForHostId(hostId);

        assertTrue(result.isEmpty());
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void testGetIncomingReservationsForHostWithNoReservations() {
        Long hostId = 1L;
        Property property = new Property();
        property.setId(1L);
        Account host = new Account();
        host.setId(hostId);
        property.setHost(host);

        when(propertyRepository.findAll()).thenReturn(Collections.singletonList(property));
        when(reservationRepository.findByPropertyId(property.getId())).thenReturn(Collections.emptyList());

        List<Reservation> result = service.getIncomingReservationsForHostId(hostId);

        assertEquals(0, result.size());
        verify(propertyRepository).findAll();
        verify(reservationRepository).findByPropertyId(property.getId());
    }

    @Test
    void testGetAllAcceptedReservationsSuccess() {
        Long guestId = 1L;
        Account guest = new Account();
        guest.setId(guestId);
        Reservation acceptedReservation = new Reservation();
        acceptedReservation.setId(1L);
        acceptedReservation.setGuest(guest);
        acceptedReservation.setStatus(ReservationStatus.ACCEPTED);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(acceptedReservation));

        List<Reservation> result = service.getAllAcceptedReservations(guestId);

        assertEquals(1, result.size());
        assertEquals(acceptedReservation, result.get(0));
        verify(reservationRepository).findAll();
    }
    @Test
    void testGetAllAcceptedReservationsWithNoReservations() {
        Long guestId = 1L;
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());
        List<Reservation> result = service.getAllAcceptedReservations(guestId);

        assertTrue(result.isEmpty());
        verify(reservationRepository).findAll();
    }

    @Test
    void testGetAllAcceptedReservationsWithDifferentStatus() {
        Long guestId = 1L;

        Account guest = new Account();
        guest.setId(guestId);
        Reservation pendingReservation = new Reservation();
        pendingReservation.setId(1L);
        pendingReservation.setGuest(guest);
        pendingReservation.setStatus(ReservationStatus.PENDING);

        Reservation rejectedReservation = new Reservation();
        rejectedReservation.setId(2L);
        rejectedReservation.setGuest(guest);
        rejectedReservation.setStatus(ReservationStatus.REJECTED);

        Reservation cancelledReservation = new Reservation();
        cancelledReservation.setId(3L);
        cancelledReservation.setGuest(guest);
        cancelledReservation.setStatus(ReservationStatus.CANCELLED);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(pendingReservation, rejectedReservation));

        List<Reservation> result = service.getAllAcceptedReservations(guestId);

        assertTrue(result.isEmpty());
        verify(reservationRepository).findAll();
    }
    @Test
    void testGetAllAcceptedReservationsWhenGuestNotFound() {
        Long guestId = 1L;
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());
        List<Reservation> result = service.getAllAcceptedReservations(guestId);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveWithExistingReservation() {
        Reservation existingReservation = new Reservation();
        existingReservation.setId(1L);
        existingReservation.setStatus(ReservationStatus.PENDING);

        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(1L);
        updatedReservation.setStatus(ReservationStatus.ACCEPTED);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existingReservation));

        service.save(updatedReservation);

        assertEquals(ReservationStatus.ACCEPTED, updatedReservation.getStatus());
        verify(reservationRepository).saveAndFlush(existingReservation);
    }
    @Test
    void testSaveWithReservationNotFound() {
        Reservation newReservation = new Reservation();
        newReservation.setId(1L);
        newReservation.setStatus(ReservationStatus.PENDING);

        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.save(newReservation),
                "Expected save to throw RuntimeException"
        );

        assertEquals("Reservation not found with id: 1", exception.getMessage());
    }


    @ParameterizedTest
    @MethodSource("updateReservationsSource")
    void testUpdateReservations(Reservation paramReservation, Reservation existingReservation, ReservationStatus expectedStatus) {
        lenient().when(reservationRepository.findByPropertyId(anyLong())).thenReturn(List.of(paramReservation, existingReservation));
        lenient().when(reservationRepository.findById(existingReservation.getId())).thenReturn(Optional.of(existingReservation));

        service.updateReservations(paramReservation);

        assertEquals(expectedStatus, existingReservation.getStatus());
    }

    static Stream<Arguments> updateReservationsSource() {
        Property property = Property.builder().id(1L).minGuests(3).maxGuests(5).build();
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setStartDate(LocalDate.of(2024, 1, 1));
        reservation1.setEndDate(LocalDate.of(2024, 1, 5));
        reservation1.setProperty(property);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setStartDate(LocalDate.of(2024, 1, 3));
        reservation2.setEndDate(LocalDate.of(2024, 1, 6));
        reservation2.setStatus(ReservationStatus.PENDING);
        reservation2.setProperty(property);

        Reservation reservation3 = new Reservation();
        reservation3.setId(3L);
        reservation3.setStartDate(LocalDate.of(2024, 1, 7));
        reservation3.setEndDate(LocalDate.of(2024, 1, 10));
        reservation3.setProperty(property);

        Reservation reservation4 = new Reservation();
        reservation4.setId(4L);
        reservation4.setStartDate(LocalDate.of(2024, 1, 1));
        reservation4.setEndDate(LocalDate.of(2024, 1, 3));
        reservation4.setStatus(ReservationStatus.PENDING);
        reservation4.setProperty(property);

        return Stream.of(
                Arguments.of(reservation1, reservation2, ReservationStatus.REJECTED),
                Arguments.of(reservation3 , reservation4, ReservationStatus.PENDING)
        );
    }

    @ParameterizedTest
    @MethodSource("acceptOrRejectReservationSource")
    void testAcceptOrRejectReservation(Reservation reservation, ReservationStatus isAccepted, ReservationStatus expectedStatus) {
        Property property = new Property();
        List<PropertyAvailabilityEntry> entries = List.of(
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(2), 100.0, property),
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(3), 100.0, property),
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(4), 100.0, property)
        );
        property.setAvailabilityEntries(entries);
        reservation.setProperty(property);
        lenient().when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        service.acceptOrRejectReservation(reservation, isAccepted);

        assertEquals(expectedStatus, reservation.getStatus());
    }

    static Stream<Arguments> acceptOrRejectReservationSource() {
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setStartDate(LocalDate.of(2024, 1, 1));
        reservation1.setEndDate(LocalDate.of(2024, 1, 5));

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setStartDate(LocalDate.of(2024, 1, 3));
        reservation2.setEndDate(LocalDate.of(2024, 1, 6));
        reservation2.setStatus(ReservationStatus.PENDING);

        return Stream.of(
                Arguments.of(reservation1, ReservationStatus.ACCEPTED, ReservationStatus.ACCEPTED),
                Arguments.of(reservation2, ReservationStatus.REJECTED, ReservationStatus.REJECTED)
        );
    }

    @ParameterizedTest
    @MethodSource("cancelReservationSource")
    void testCancelReservation(Reservation reservation, boolean expectedResult) {
        LocalDateTime fixedLocalDateTime = LocalDateTime.now();
        lenient().when(clock.instant()).thenReturn(fixedLocalDateTime.toInstant(ZoneOffset.UTC));
        lenient().when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        Property property = new Property();
        List<PropertyAvailabilityEntry> entries = List.of(
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(2), 100.0, property),
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(3), 100.0, property),
                new PropertyAvailabilityEntry(LocalDate.now().plusDays(4), 100.0, property)
        );
        property.setAvailabilityEntries(entries);
        reservation.setProperty(property);
        lenient().when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        boolean result = service.cancelReservation(reservation);

        assertEquals(expectedResult, result);
        ReservationStatus expectedStatus = expectedResult ? ReservationStatus.CANCELLED : reservation.getStatus();
        assertEquals(expectedStatus, reservation.getStatus());
    }

    static Stream<Arguments> cancelReservationSource() {
        Property property = Property.builder().id(1L).cancellationDeadline(2).build();

        Reservation pastDeadlineReservation = new Reservation();
        pastDeadlineReservation.setId(1L);
        pastDeadlineReservation.setStartDate(LocalDate.now().minusDays(10));
        pastDeadlineReservation.setProperty(property);

        Reservation withinDeadlineReservation = new Reservation();
        withinDeadlineReservation.setId(2L);
        withinDeadlineReservation.setStartDate(LocalDate.now().plusDays(10));
        withinDeadlineReservation.setProperty(property);

        return Stream.of(
                Arguments.of(pastDeadlineReservation, false),
                Arguments.of(withinDeadlineReservation, true)
        );
    }
}