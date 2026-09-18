package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.ReservationDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationRequestDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationSearchRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO)
    {
        ReservationDTO reservationDTO = reservationService.createReservation(reservationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<ReservationDTO> createReservationForUser(@PathVariable("userId") Long userId,
                                                                   @Valid @RequestBody ReservationRequestDTO reservationRequestDTO)
    {
        ReservationDTO reservationDTO = reservationService.createReservationForUser(userId,reservationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("id") Long reservationId)
    {
        ReservationDTO reservationDTO = reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(reservationDTO);
    }

    @PutMapping("/{id}/fulfill")
    public ResponseEntity<ReservationDTO> fulfillReservation(@PathVariable("id") Long reservationId)
    {
        ReservationDTO reservationDTO = reservationService.fulfillReservation(reservationId);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping("/my")
    public ResponseEntity<PageResponseDTO<ReservationDTO>> getMyReservations(@Valid @ModelAttribute ReservationSearchRequestDTO reservationSearchRequestDTO)
    {
        PageResponseDTO<ReservationDTO> myReservations = reservationService.getMyReservations(reservationSearchRequestDTO);
        return ResponseEntity.ok(myReservations);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<ReservationDTO>> getAllReservations(@Valid @ModelAttribute ReservationSearchRequestDTO reservationSearchRequestDTO)
    {
        PageResponseDTO<ReservationDTO> allReservations = reservationService.searchReservations(reservationSearchRequestDTO);
        return ResponseEntity.ok(allReservations);
    }
}
