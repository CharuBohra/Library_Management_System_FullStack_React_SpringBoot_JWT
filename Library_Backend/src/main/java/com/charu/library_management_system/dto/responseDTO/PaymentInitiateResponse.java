package com.charu.library_management_system.dto.responseDTO;

import com.charu.library_management_system.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInitiateResponse {
    private Long paymentId;

    private String transactionId;

    private PaymentGateway paymentGateway;

    private String razorpayOrderId;

    private BigDecimal amount;

    private String description;

    private String checkoutUrl;

    private String message;

    private Boolean success;
}
