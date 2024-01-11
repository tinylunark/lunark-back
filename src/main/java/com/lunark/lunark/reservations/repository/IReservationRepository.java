package com.lunark.lunark.reservations.repository;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reports.dto.PropertyReportResponseDto;
import com.lunark.lunark.reservations.model.Reservation;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
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

    @Query("select new com.lunark.lunark.reports.model.MonthlyReport(extract(month from r.endDate), sum(r.price), count(*)) " +
            "from Reservation r " +
            "where extract(year from r.endDate) = :year " +
            "and r.property.id = :property_id " +
            "and r.endDate < current_date " +
            "and r.status = 1 " +
            "group by extract(month from r.endDate) " +
            "order by extract(month from r.endDate) ")
    Collection<PropertyReportResponseDto> generatePropertyReport(@Param("property_id") Long propertyId, @Param("year") Integer year);
}
