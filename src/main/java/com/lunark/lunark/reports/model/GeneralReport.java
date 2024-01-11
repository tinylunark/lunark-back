package com.lunark.lunark.reports.model;

import com.lunark.lunark.reservations.model.Reservation;
import lombok.Data;

import java.util.Collection;

@Data
public class GeneralReport {
    Integer reservationCount;
    Double profit;
    Collection<Reservation> reservations;
}
