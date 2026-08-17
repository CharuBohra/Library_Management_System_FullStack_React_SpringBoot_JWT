package com.charu.library_management_system.dto.requestDTO;

import com.charu.library_management_system.enums.PaymentGateway;
import com.charu.library_management_system.enums.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInitiateRequest {
    private Long userId;

    private PaymentType paymentType;

    private PaymentGateway paymentGateway;

    @NotNull
    @DecimalMin(value = "0.00",message = "Price of the book cannot be negative")
    @Digits(integer = 10 , fraction = 2,message = "price must have only 2 decimal values and 10 integer places")
    private BigDecimal amount;

    private String description;

    private Long fineId;

    private Long subscriptionId;

    private String successUrl;

    private String cancelUrl;
}
