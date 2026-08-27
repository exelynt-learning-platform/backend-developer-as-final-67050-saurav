package com.exelynt.booking.controller;

import com.exelynt.booking.dto.CreateReservationRequest;
import com.exelynt.booking.dto.ReservationDTO;
import com.exelynt.booking.entity.ReservationStatus;
import com.exelynt.booking.service.ReservationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    // USER and ADMIN can create reservations
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(
            @Valid @RequestBody CreateReservationRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        return new ResponseEntity<>(
                reservationService.createReservation(request, username),
                HttpStatus.CREATED
        );
    }


    // ADMIN sees all reservations
    // USER sees only their own reservations
    @GetMapping
    public ResponseEntity<Page<ReservationDTO>> getReservations(

            @RequestParam(required = false)
            ReservationStatus status,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "startTime")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir,

            Authentication authentication) {

        if (page < 0) {
            page = 0;
        }

        if (size < 1 || size > 100) {
            size = 10;
        }

        Sort sort;

        if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        PageRequest pageable =
                PageRequest.of(page, size, sort);

        String username = authentication.getName();

        return ResponseEntity.ok(
                reservationService.getReservations(
                        status,
                        minPrice,
                        maxPrice,
                        pageable,
                        username
                )
        );
    }


    // Get one reservation
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(
            @PathVariable Long id,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                reservationService.getReservationById(
                        id,
                        username
                )
        );
    }


    // USER can cancel own reservation
    // ADMIN can cancel any reservation
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(
            @PathVariable Long id,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                reservationService.cancelReservation(
                        id,
                        username
                )
        );
    }


    // Only ADMIN can confirm
    @PutMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationDTO> confirmReservation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.confirmReservation(id)
        );
    }


    // Only ADMIN can delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id) {

        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}