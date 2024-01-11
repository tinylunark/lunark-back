package com.lunark.lunark.reports.model;

import com.lunark.lunark.reservations.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class GeneralReport {
    Integer reservationCount;
    Double profit;
}
