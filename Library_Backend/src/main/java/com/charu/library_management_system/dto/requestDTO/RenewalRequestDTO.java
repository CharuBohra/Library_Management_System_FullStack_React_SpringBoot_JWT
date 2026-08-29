package com.charu.library_management_system.dto.requestDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenewalRequestDTO {

    @NotNull(message = "Book Loan Id is required")
    private Long BookLoanId;

    @Builder.Default
    @Min(value = 1, message = "Extension Days should be greater than or equal to one")
    private Integer extensionDays = 14;

    @Size(max = 500,message = "Notes should not exceed 500 characters")
    private String notes;
}
