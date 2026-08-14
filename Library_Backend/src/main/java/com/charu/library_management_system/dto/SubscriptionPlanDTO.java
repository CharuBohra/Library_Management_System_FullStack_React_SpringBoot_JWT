package com.charu.library_management_system.dto;

import jakarta.validation.constraints.*;
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
public class SubscriptionPlanDTO {
    private Long id;

    @NotBlank(message = "Subscription planCode is mandatory")
    private String planCode;

    @NotBlank(message = "Subscription plan name is mandatory")
    private String name;

    @Size(max = 500, message = "Maximum Length of description is 500 characters")
    private String description;

    @NotNull(message = "Subscription price is mandatory")
    @DecimalMin(value = "0.00",message = "Price of the plan cannot be negative")
    @Digits(integer = 10 , fraction = 2,message = "plan price must have only 2 decimal values and 10 integer places")
    private BigDecimal price;

    @NotBlank(message = "Currency for any subscription plan is required")
    @Builder.Default
    private String currency = "INR";

    @NotNull(message = "maxBooksAllowed for subscription plan is mandatory")
    @Positive
    private Integer maxBooksAllowed;

    @NotNull(message = "maxDaysPerBook for subscription plan is mandatory")
    @Positive
    private Integer maxDaysPerBook;

    @NotNull(message = "durationDays for subscription plan is required")
    @Positive
    private Integer durationDays;

    @Builder.Default
    @PositiveOrZero
    private Integer displayOrder=0;

    private String badgeText;

    private String adminNotes;

    @NotNull
    @Builder.Default
    private Boolean active = true;

    @NotNull
    @Builder.Default
    private Boolean isFeatured = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;
}
