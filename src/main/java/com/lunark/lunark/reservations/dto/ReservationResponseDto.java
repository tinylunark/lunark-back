package com.lunark.lunark.reservations.dto;

import com.lunark.lunark.properties.dto.PropertyResponseDto;
import com.lunark.lunark.properties.model.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {
    PropertyResponseDto property;
    LocalDate startDate;
    LocalDate endDate;
    double price;
    int numberOfGuests;
}
