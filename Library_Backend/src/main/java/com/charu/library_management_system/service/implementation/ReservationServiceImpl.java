package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.ReservationDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationRequestDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationSearchRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {


    @Override
    public ReservationDTO createReservation(ReservationRequestDTO reservationRequest) {

        return null;
    }

    @Override
    public ReservationDTO createReservationForUser(Long userId, ReservationRequestDTO reservationRequest) {
        return null;
    }

    @Override
    public ReservationDTO cancelReservation(Long reservationId) {
        return null;
    }

    @Override
    public ReservationDTO fulfillReservation(Long reservationId) {
        return null;
    }

    @Override
    public PageResponseDTO<ReservationDTO> getMyReservations(ReservationSearchRequestDTO reservationSearchRequest) {
        return null;
    }

    @Override
    public PageResponseDTO<ReservationDTO> searchReservations(ReservationSearchRequestDTO reservationSearchRequest) {
        return null;
    }
}
