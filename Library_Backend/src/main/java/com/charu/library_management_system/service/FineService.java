package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import com.charu.library_management_system.enums.FineStatus;
import com.charu.library_management_system.enums.FineType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface FineService {

    FineDTO createFine(CreateFineRequest createFineRequest);

    PaymentInitiateResponse payFine(Long fineId, String transactionId);

    void markFineAsPaid(Long fineId , BigDecimal amount , String transactionId);

    FineDTO waiveFine(WaiveFineRequest waiveFineRequest);

    List<FineDTO> getMyFine(FineStatus status , FineType type);

    PageResponseDTO<FineDTO> getAllFine(FineStatus status , FineType type , Long userId, int page , int size);
}
