package com.lunark.lunark.reservations.model;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Future
    LocalDate startDate;
    @Future
    LocalDate endDate;
    @Positive
    int numberOfGuests;
    @NotNull
    ReservationStatus status;
    @Positive
    double price;
    @ManyToOne(fetch = FetchType.LAZY)
    Property property;
    @ManyToOne(fetch = FetchType.LAZY)
    Account guest;
}
