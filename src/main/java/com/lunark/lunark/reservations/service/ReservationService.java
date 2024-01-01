package com.lunark.lunark.reservations.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import com.lunark.lunark.reservations.dto.ReservationDto;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.model.ReservationStatus;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {
    private final IReservationRepository reservationRepository;
    private final IPropertyRepository propertyRepository;
    private final IAccountRepository accountRepository;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository, IPropertyRepository propertyRepository, IAccountRepository accountRepository) {
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Reservation create(ReservationRequestDto reservationDto, String username) {
        Optional<Property> property = propertyRepository.findById(reservationDto.getPropertyId());
        if (property.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "property not found");
        }

        if (!property.get().isAvailable(reservationDto.getStartDate(), reservationDto.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "property not available for given date range");
        }

        if (property.get().getMinGuests() > reservationDto.getNumberOfGuests() || property.get().getMaxGuests() < reservationDto.getNumberOfGuests()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "number of guests does not match the defined guest range of property");
        }

        Optional<Account> guest = accountRepository.findByEmail(username);
        if (guest.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user does not exist");
        }

        double totalPrice = calculatePrice(
                reservationDto.getStartDate(),
                reservationDto.getEndDate(),
                property.get().getAvailabilityEntries()
        );

        Reservation reservation = Reservation.builder()
                .price(totalPrice)
                .guest(guest.get())
                .property(property.get())
                .status(property.get().isAutoApproveEnabled() ? ReservationStatus.ACCEPTED : ReservationStatus.PENDING)
                .numberOfGuests(reservationDto.getNumberOfGuests())
                .startDate(reservationDto.getStartDate())
                .endDate(reservationDto.getEndDate())
                .build();

        return this.reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> getAllReservationsForPropertiesList(List<Property> propertiesList) {
        List<Reservation> reservationsForProperties = new ArrayList<>();
        List<Reservation> allReservations = reservationRepository.findAll();
        for(Property property : propertiesList) {
            for(Reservation reservation : allReservations) {
                if (reservation.getProperty() != null && reservation.getProperty().getId().equals(property.getId())) {
                    reservationsForProperties.add(reservation);
                }
            }
        }
        return reservationsForProperties;
    }

    @Override
    public List<Reservation> getIncomingReservationsForHostId(Long hostId) {
        List<Property> properties = propertyRepository.findAll().stream().filter(property -> Objects.equals(property.getHost().getId(), hostId)).toList();
        List<Reservation> reservationsForProperties = new ArrayList<>();
        for(Property property: properties) {
            List<Reservation> reservationForProperty = reservationRepository.findByPropertyId(property.getId());
            reservationsForProperties.addAll(reservationForProperty);
        }
        return reservationsForProperties;
    }

    @Override
    public List<Reservation> getAllAcceptedReservations(Long guestId) {
        return reservationRepository.findAll().stream().filter(reservation -> Objects.equals(reservation.getGuest().getId(), guestId) && reservation.getStatus() == ReservationStatus.ACCEPTED).toList();
    }

    @Override
    public void save(Reservation reservation) {
        Optional<Reservation> reservationUpdate = findById(reservation.getId());
        if (reservationUpdate.isPresent()) {
            Reservation existingReservation = reservationUpdate.get();
            existingReservation.copyFields(reservation);
            reservationRepository.saveAndFlush(existingReservation);
        } else {
            throw new RuntimeException("Reservation not found with id: " + reservation.getId());
        }
    }

    @Override
    public void updateReservations(Reservation reservation) {
        Long propertyId = reservation.getProperty().getId();
        List<Reservation> allPropertyReservations = reservationRepository.findByPropertyId(propertyId);

        for (Reservation existingReservation : allPropertyReservations) {
            if (doDatesOverlap(reservation, existingReservation)) {
                existingReservation.setStatus(ReservationStatus.REJECTED);
                reservationRepository.save(existingReservation);
            }
        }
    }

    @Override
    public boolean cancelReservation(Reservation reservation) {
        if (isPastCancellationDeadline(reservation)) {
            return false;
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        save(reservation);
        return true;
    }

    private boolean isPastCancellationDeadline(Reservation reservation) {
        Property property = reservation.getProperty();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime cancellationDeadline = reservation.getStartDate().minusDays(property.getCancellationDeadline()).atStartOfDay();
        return currentDateTime.isAfter(cancellationDeadline);
    }

    private boolean doDatesOverlap(Reservation newReservation, Reservation existingReservation) {
        return newReservation.getStartDate().isBefore(existingReservation.getEndDate()) &&
                existingReservation.getStartDate().isBefore(newReservation.getEndDate()) ||
                newReservation.getStartDate().isEqual(existingReservation.getEndDate()) ||
                newReservation.getEndDate().isEqual(existingReservation.getStartDate());
    }


    @Override
    public List<Reservation> getAllReservationsForUser(Long userId) {
        Account account = this.accountRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found"));
        return account.getReservations().stream().toList();
    }

    private double calculatePrice(LocalDate start, LocalDate end, Collection<PropertyAvailabilityEntry> entries) {
        Collection<LocalDate> datesToBook = start.datesUntil(end.plusDays(1)).toList();

        return entries.stream()
                .filter(entry -> datesToBook.contains(entry.getDate()))
                .mapToDouble(PropertyAvailabilityEntry::getPrice)
                .sum();
    }

}
