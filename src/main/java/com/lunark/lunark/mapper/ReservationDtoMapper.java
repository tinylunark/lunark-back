package com.lunark.lunark.mapper;


import com.lunark.lunark.reservations.dto.ReservationDto;
import com.lunark.lunark.reservations.model.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationDtoMapper {
    private static ModelMapper modelMapper;
    @Autowired
    public ReservationDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Reservation fromDtoToReservation(ReservationDto reservationDto) {
        return modelMapper.map(reservationDto, Reservation.class);
    }

    public static ReservationDto fromReservationToDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getProperty().getId(),
                reservation.getProperty().getName(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getPrice(),
                reservation.getGuest().getId(),
                reservation.getNumberOfGuests(),
                reservation.getStatus().toString(),
                reservation.getProperty().getCancellationDeadline()
        );
    }
}
