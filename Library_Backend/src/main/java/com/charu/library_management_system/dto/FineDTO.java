package com.charu.library_management_system.dto;

import com.charu.library_management_system.enums.FineStatus;
import com.charu.library_management_system.enums.FineType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FineDTO {

    private Long id;

    @NotNull(message = "Book Loan ID is mandatory")
    private Long bookLoanId;

    private String bookTitle;

    private String bookIsbn;

    private Long userId;

    private String userName;

    private String userEmail;

    @NotNull(message = "Fine type is required to create fine")
    private FineType type;

    @NotNull(message = "Amount is required")
    @Positive
    @Digits(integer = 10, fraction = 2,
            message = "Amount must have up to 10 integer digits and 2 decimal places")
    private BigDecimal amount;

    @PositiveOrZero(message = "Amount paid cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amountPaid;

    @PositiveOrZero(message = "Amount Outstanding cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amountOutstanding;

    private FineStatus status;

    @Size(max = 500, message = "Reason should not exceed 500 characters")
    private String reason;

    @Size(max = 1000, message = "Notes should not exceed 1000 characters")
    private String notes;

    private Long waivedByUserId;

    private String waivedByUserName;

    private String waivedByUserEmail;

    private LocalDateTime waivedAt;

    @Size(max = 500,message = "Waiver Reason should not exceed more than 500 characters")
    private String waiverReason;

    private LocalDateTime paidAt;

    private Long processedByUserId;

    private String processedByUserName;

    private String processedByUserEmail;

    private String transactionId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
