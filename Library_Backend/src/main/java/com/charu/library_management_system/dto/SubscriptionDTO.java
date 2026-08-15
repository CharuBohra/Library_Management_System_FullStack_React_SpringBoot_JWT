package com.charu.library_management_system.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    private Long id;

    private Long userId;

    private String userName;

    private String userEmail;

    @NotNull(message = "Plan id is required")
    private Long planId;

    private String planName;

    private String planCode;

    @DecimalMin(value = "0.00",message = "Price of the book cannot be negative")
    @Digits(integer = 10 , fraction = 2,message = "price must have only 2 decimal values and 10 integer places")
    private BigDecimal price;

    @Builder.Default
    @Size(min = 3, max = 3, message = "Currency code should be of 3 characters only")
    private String currency="INR";

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder.Default
    private Boolean active = false;

    private Integer maxBooksAllowed;

    private Integer maxDaysPerBook;

    @Builder.Default
    private  Boolean autoRenew = false;

    private LocalDateTime cancelledAt;

    private String cancellationReason;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long daysRemaining;

    private Boolean isExpired;

    private Boolean isValid;
}
