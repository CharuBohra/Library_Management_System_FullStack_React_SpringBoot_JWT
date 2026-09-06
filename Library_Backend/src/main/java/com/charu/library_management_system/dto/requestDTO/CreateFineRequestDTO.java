package com.charu.library_management_system.dto.requestDTO;

import com.charu.library_management_system.enums.FineType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFineRequestDTO {

    @NotNull(message = "Book Loan ID is mandatory to create fine")
    private Long bookLoanId;

    @NotNull(message = "Fine type is required to create fine")
    private FineType type;

    @NotNull(message = "amount is mandatory")
    @Positive(message = "Amount must be greater than zero")
    @Digits(integer = 10,
            fraction = 2,
            message =  "Amount must have up to 10 integer digits and 2 decimal places")
    private BigDecimal amount;

    @Size(max = 500,message = "Fine Reason cannot exceed more than 500 characters")
    private String reason;

    @Size(max = 1000, message = "Notes should not exceed 1000 characters")
    private String notes;
}
