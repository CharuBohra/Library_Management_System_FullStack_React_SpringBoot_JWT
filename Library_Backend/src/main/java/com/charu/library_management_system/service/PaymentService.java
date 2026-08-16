package com.charu.library_management_system.service;

import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest paymentInitiateRequest);

    PaymentDTO verifyPayment(PaymentVerifyRequest paymentVerifyRequest);

    Page<PaymentDTO> getAllPayments(Pageable pageable);
}
