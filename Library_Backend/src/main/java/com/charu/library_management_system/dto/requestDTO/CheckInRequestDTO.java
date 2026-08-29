package com.charu.library_management_system.dto.requestDTO;

import com.charu.library_management_system.enums.BookLoanStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRequestDTO {

    @NotNull(message = "Book Loan Id is required")
    private Long bookLoanId;

    @Builder.Default
    private BookLoanStatus condition = BookLoanStatus.RETURNED;

    @Size(max = 500, message = "Notes length should not exceed 500 characters")
    private String notes;
}
