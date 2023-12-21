package com.lunark.lunark.reservations.service;

import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.model.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation create(ReservationRequestDto reservation, String username);

    List<Reservation> getAllReservationsForPropertiesList(List<Property> propertiesList);

    List<Reservation> getAllReservationsForUser(Long userId);
}
