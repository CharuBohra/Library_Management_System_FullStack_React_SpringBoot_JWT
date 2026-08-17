package com.charu.library_management_system.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerifyRequest {

    private String razorPaymentId;

    private String stripePaymentIntentId;

    private String stripePaymentIntentStatus;
}
