package com.lunark.lunark.reservations.repository;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reservations.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    @Query("select r from Reservation r where r.guest.id = :guest_id and r.property.id = :property_id and r.status = 1 and r.endDate between :from and CURRENT_DATE")
    Collection<Reservation> findAllPastReservationsAtPropertyForGuestAfterDate(@Param("guest_id") Long guestId, @Param("property_id") Long propertyId, @Param("from") LocalDate from);

    Optional<Reservation> findById(Long id);
    List<Reservation> findByPropertyId(Long propertyId);
    @Query("select r from Reservation r where r.guest.id = :guest_id and r.property.host.id = :host_id and r.status = 1 and r.endDate between :from and CURRENT_DATE")
    Collection<Reservation> findAllPastReservationsAtHostAfterDate(@Param("guest_id") Long guestId, @Param("host_id") Long hostId, @Param("from") LocalDate from);
}
