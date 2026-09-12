package com.charu.library_management_system.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {

    @NotNull(message = "Book Id is required for Reservation")
    private Long bookId;

    private String notes;
}
