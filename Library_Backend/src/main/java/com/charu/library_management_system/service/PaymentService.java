package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.PaymentDTO;
import com.charu.library_management_system.dto.requestDTO.PaymentInitiateRequest;
import com.charu.library_management_system.dto.requestDTO.PaymentVerifyRequest;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest paymentInitiateRequest);

    PaymentDTO verifyPayment(PaymentVerifyRequest paymentVerifyRequest);

    PageResponseDTO<PaymentDTO> getAllPayments(Pageable pageable);
}
