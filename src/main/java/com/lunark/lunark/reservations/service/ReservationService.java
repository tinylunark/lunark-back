package com.lunark.lunark.reservations.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.model.ReservationStatus;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                property.get().getAvailabilityEntries().stream().toList()
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

    private double calculatePrice(LocalDate start, LocalDate end, List<PropertyAvailabilityEntry> entries) {
        List<LocalDate> datesToBook = start.datesUntil(end.plusDays(1)).toList();

        return entries.stream()
                .filter(entry -> datesToBook.contains(entry.getDate()))
                .mapToDouble(PropertyAvailabilityEntry::getPrice)
                .sum();
    }
}
