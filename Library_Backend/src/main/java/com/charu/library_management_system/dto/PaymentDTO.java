package com.charu.library_management_system.dto;

import com.charu.library_management_system.enums.PaymentGateway;
import com.charu.library_management_system.enums.PaymentStatus;
import com.charu.library_management_system.enums.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long id;

    private Long userId;

    private String userName;

    private String userEmail;

    private Long subscriptionId;

    private String planName;

    private String planCode;

    @NotNull
    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    @NotNull
    private PaymentGateway paymentGateway;

    @NotNull
    @DecimalMin(value = "0.00",message = "Price of the book cannot be negative")
    @Digits(integer = 10, fraction = 2,message = "price must have only 2 decimal values and 10 integer places")
    private BigDecimal amount;

    @Builder.Default
    private String currency = "INR";

    private String transactionId;

    private String gatewayPaymentId;

    private String gatewayOrderId;

    private String gatewayPaymentSignature;

    private String description;

    private String failureReason;

    @Builder.Default
    private Integer retryCount =0;

    private LocalDateTime initiatedAt;

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
