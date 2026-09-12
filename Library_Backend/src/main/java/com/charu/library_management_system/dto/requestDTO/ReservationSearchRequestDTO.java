package com.charu.library_management_system.dto.requestDTO;

import com.charu.library_management_system.enums.ReservationStatus;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSearchRequestDTO {

    @Positive(message = "User id cannot be negative")
    private Long userId;

    @Positive(message = "Book id cannot be negative")
    private Long bookId;

    private ReservationStatus status;

    private Boolean activeOnly;

    @Builder.Default
    private Integer page=0;

    @Builder.Default
    private Integer size=20;

    private String sortBy = "reservedAt";

    private String sortDirection = "DESC";
}
