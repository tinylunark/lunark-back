package com.lunark.lunark.reservations.service;

import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.model.Reservation;

public interface IReservationService {
    Reservation create(ReservationRequestDto reservation, String username);
}
