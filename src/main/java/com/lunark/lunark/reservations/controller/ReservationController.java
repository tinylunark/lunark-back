package com.lunark.lunark.reservations.controller;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.mapper.ReservationDtoMapper;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.service.IPropertyService;
import com.lunark.lunark.reservations.dto.ReservationResponseDto;
import com.lunark.lunark.reservations.dto.ReservationDto;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.model.ReservationStatus;
import com.lunark.lunark.reservations.service.IReservationService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final IReservationService reservationService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationController(IReservationService reservationService, ModelMapper modelMapper) {
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<ReservationResponseDto> createReservation(@Valid @RequestBody ReservationRequestDto dto) {
        Account user = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Reservation reservation = this.reservationService.create(dto, user.getUsername());
        ReservationResponseDto response = modelMapper.map(reservation, ReservationResponseDto.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{reservation_id}")
    public ResponseEntity<ReservationDto> getReservation(@RequestHeader("x-access-token") String token, @PathVariable("reservation_id") Long Id) {
        ReservationDto reservationDto = new ReservationDto(2L, 1L, "Vila Golija", LocalDate.of(2024, 6, 14), LocalDate.of(2023, 6, 16), 9959, 1L, 3, "pending", 0);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{reservation_id}")
    public ResponseEntity<ReservationDto> deleteReservation(@RequestHeader("x-access-token") String guestToken, @PathVariable("reservation_id") Long Id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/accept/{reservation_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> acceptReservation(@PathVariable("reservation_id") Long id) {
        return reservationService.findById(id).map(reservation -> acceptOrRejectReservation(reservation, ReservationStatus.ACCEPTED)).orElse(notFoundResponse());
    }

    private ResponseEntity<ReservationDto> acceptOrRejectReservation(Reservation reservation, ReservationStatus status) {
        reservation.setStatus(status);
        reservationService.save(reservation);
        if(status == ReservationStatus.ACCEPTED) {reservationService.updateReservations(reservation);}
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<ReservationDto> notFoundResponse() {
        return ResponseEntity.notFound().build();
    }


    @PostMapping(path = "/reject/{reservation_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> rejectReservation(@PathVariable("reservation_id") Long id) {
        return reservationService.findById(id).map(reservation -> acceptOrRejectReservation(reservation, ReservationStatus.REJECTED)).orElse(notFoundResponse());
    }


    @PostMapping(path = "/cancel/{reservation_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> cancelReservation(@RequestHeader("x-access-token") String guest, @PathVariable("reservation_id") Long id) {
        ReservationDto reservationDto = new ReservationDto(2L, 1L, "Vila Golija", LocalDate.of(2024, 6, 14), LocalDate.of(2023, 6, 16), 9959, 1L, 3, "rejected", 0);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @GetMapping(value="")
    public ResponseEntity<Collection<ReservationDto>> getReservations(@RequestHeader("x-access-token") String token, @RequestParam(value = "propertyName", required = false) String propertyName, @RequestParam(value = "date", required = false) LocalDate date, @RequestParam(value = "status", required = false) String status){
        ArrayList<ReservationDto> reservationDtos = new ArrayList<>();
        reservationDtos.add(new ReservationDto(1L, 2L, "Hotel Oderberger", LocalDate.of(2024, 7, 14), LocalDate.of(2023, 7, 16), 15000, 1L, 1, "canceled", 1));

        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @GetMapping(value="/incoming-reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('HOST')")
    public ResponseEntity<List<ReservationDto>> getIncomingReservations(@RequestParam("hostId") Long hostId, SpringDataWebProperties pageable) {
        List<Reservation> reservations = reservationService.getIncomingReservationsForHostId(hostId).stream().filter(reservation -> ReservationStatus.PENDING.equals(reservation.getStatus())).toList();
        System.out.println(reservations.size());
        if(reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ReservationDto> reservationDtos = reservations.stream().map(ReservationDtoMapper::fromReservationToDto) .toList();
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }
}
