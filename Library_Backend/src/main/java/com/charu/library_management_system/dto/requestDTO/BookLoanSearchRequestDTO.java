package com.charu.library_management_system.dto.requestDTO;

import com.charu.library_management_system.enums.BookLoanStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanSearchRequestDTO {

    private Long userId;

    private Long bookId;

    private BookLoanStatus status;

    private Boolean overdueOnly;

    private Boolean unpaidFineOnly;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Builder.Default
    @Min(value = 0, message = "Page cannot be negative")
    private Integer page=0;

    @Builder.Default
    @Min(value = 1, message = "Size must be at least 1")
    @Max(value = 100, message = "Size cannot exceed 100")
    private Integer size = 20;

    @Builder.Default
    private String sortBy = "createdAt";

    @Builder.Default
    private String sortDir = "DESC";
}
