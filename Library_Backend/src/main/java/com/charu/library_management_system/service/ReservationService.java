package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.ReservationDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

     ReservationDTO createReservation (ReservationRequestDTO reservationRequest);

     ReservationDTO createReservationForUser(Long userId , ReservationRequestDTO reservationRequest);

     ReservationDTO cancelReservation(Long reservationId);

     ReservationDTO fulfillReservation(Long reservationId);

     PageResponseDTO<ReservationDTO> getMyReservations(ReservationSearchRequestDTO reservationSearchRequest);

     PageResponseDTO<ReservationDTO> searchReservations(ReservationSearchRequestDTO reservationSearchRequest);
}
