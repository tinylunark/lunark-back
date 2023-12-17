package com.lunark.lunark.reservations.model;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate startDate;
    LocalDate endDate;
    int numberOfGuests;
    ReservationStatus status;
    double price;
    @ManyToOne(fetch = FetchType.LAZY)
    Property property;
    @ManyToOne(fetch = FetchType.LAZY)
    Account guest;
}
