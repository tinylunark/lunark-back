package com.lunark.lunark.reservations.model;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql
        = "UPDATE reservation "
        + "SET deleted = true "
        + "WHERE id = ?")
@Where(clause = "deleted = false")
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
    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted = false;

    public Reservation(Long id, LocalDate startDate, LocalDate endDate, int numberOfGuests, ReservationStatus status, double price, Property property, Account guest) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.price = price;
        this.property = property;
        this.guest = guest;
    }
}
