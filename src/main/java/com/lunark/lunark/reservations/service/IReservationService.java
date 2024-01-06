package com.lunark.lunark.reservations.service;

import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reservations.dto.ReservationDto;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.model.ReservationStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IReservationService {
    Reservation create(ReservationRequestDto reservation, String username);

    Optional<Reservation> findById(Long id);

    List<Reservation> getAllReservationsForPropertiesList(List<Property> propertiesList);

    List<Reservation> getAllReservationsForUser(Long userId);

    public List<Reservation> getIncomingReservationsForHostId(Long hostId);
    public List<Reservation> getAllAcceptedReservations(Long guestId);

    void save(Reservation reservation);

    void updateReservations(Reservation reservation);

    void acceptOrRejectReservation(Reservation reservation, ReservationStatus isAccepted);

    boolean cancelReservation(Reservation reservation);
}
