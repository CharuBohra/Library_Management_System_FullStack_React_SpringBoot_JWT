package com.charu.library_management_system.dto.requestDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenewalRequestDTO {

    @NotNull(message = "Book Loan Id is required")
    private Long bookLoanId;

    @NotNull(message = "Extension Days is required for Renewal")
    @Positive(message = "Extension Days must be greater than zero")
    private Integer extensionDays;

    @Size(max = 500,message = "Notes should not exceed 500 characters")
    private String notes;
}
