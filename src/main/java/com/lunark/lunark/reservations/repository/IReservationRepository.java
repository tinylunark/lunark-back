package com.lunark.lunark.reservations.repository;

import com.lunark.lunark.reservations.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
