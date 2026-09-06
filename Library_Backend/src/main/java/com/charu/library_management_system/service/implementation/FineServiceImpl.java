package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.FineDTO;
import com.charu.library_management_system.dto.requestDTO.CreateFineRequestDTO;
import com.charu.library_management_system.dto.requestDTO.WaiveFineRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import com.charu.library_management_system.enums.FineStatus;
import com.charu.library_management_system.enums.FineType;
import com.charu.library_management_system.repository.FineRepository;
import com.charu.library_management_system.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;

    @Override
    public FineDTO createFine(CreateFineRequestDTO createFineRequest) {
        return null;
    }

    @Override
    public PaymentInitiateResponse payFine(Long fineId, String transactionId) {
        return null;
    }

    @Override
    public void markFineAsPaid(Long fineId, BigDecimal amount, String transactionId) {

    }

    @Override
    public FineDTO waiveFine(WaiveFineRequestDTO waiveFineRequest) {
        return null;
    }

    @Override
    public List<FineDTO> getMyFine(FineStatus status, FineType type) {
        return List.of();
    }

    @Override
    public PageResponseDTO<FineDTO> getAllFine(FineStatus status, FineType type, Long userId, int page, int size) {
        return null;
    }
}
