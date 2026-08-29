package com.charu.library_management_system.dto.requestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBookRequestDTO {
    @NotNull(message = "Book Id is required")
    private Long bookId;

    @Min(value = 1,message = "Minimum checkoutDays should be 1")
    private Integer checkoutDays;

    @Size(max = 500, message = "Notes length should not exceed 500 characters")
    private String notes;
}
