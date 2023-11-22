package com.lunark.lunark.controller;

import com.lunark.lunark.dto.ReservationConfirmationDto;
import com.lunark.lunark.dto.ReservationDto;
import com.lunark.lunark.dto.ReservationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationConfirmationDto> createReservation(@RequestHeader("x-access-token") String guestToken, @RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationConfirmationDto reservationConfirmationDto = new ReservationConfirmationDto("Vila Golija", LocalDate.of(2024, 6, 14), LocalDate.of(2023, 6, 16), 9959, 3);
        return new ResponseEntity<>(reservationConfirmationDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{reservation_id}")
    public ResponseEntity<ReservationDto> getReservation(@RequestHeader("x-access-token") String token, @PathVariable("reservation_id") Long Id) {
        ReservationDto reservationDto = new ReservationDto("Vila Golija", LocalDate.of(2024, 6, 14), LocalDate.of(2023, 6, 16), 9959, 1L, 3);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{reservation_id}")
    public ResponseEntity<ReservationDto> deleteReservation(@RequestHeader("x-access-token") String guestToken, @PathVariable("reservation_id") Long Id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/accept/{reservation_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acceptReservation(@RequestHeader("x-access-token") String hostToken, @PathVariable("reservation_id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/reject/{reservation_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> rejectReservation(@RequestHeader("x-access-token") String hostToken, @PathVariable("reservation_id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value="")
    public ResponseEntity<Collection<ReservationDto>> getReservations(@RequestHeader("x-access-token") String token, @RequestParam(value = "propertyName", required = false) String propertyName, @RequestParam(value = "date", required = false) LocalDate date, @RequestParam(value = "status", required = false) String status){
        ArrayList<ReservationDto> reservationDtos = new ArrayList<>();
        reservationDtos.add(new ReservationDto("Hotel Oderberger", LocalDate.of(2024, 7, 14), LocalDate.of(2023, 7, 16), 15000, 1L, 1));

        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

}
