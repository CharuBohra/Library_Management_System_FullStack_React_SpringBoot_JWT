package com.charu.library_management_system.dto;

import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.enums.BookLoanType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanDTO {

    private Long id;

    @NotNull(message = "User Id is mandatory")
    private Long userId;

    private String userName;

    private String userEmail;

    @NotNull(message = "Book id is mandatory")
    private Long bookId;

    private String bookTitle;

    private String bookIsbn;

    private String bookAuthor;

    private String bookCoverImageUrl;

    @NotNull(message = "Book Loan Type is mandatory")
    private BookLoanType type;

    @NotNull(message = "Book Loan Status is mandatory ")
    private BookLoanStatus status;

    private LocalDateTime checkoutDate;

    private LocalDateTime dueDate;

    private Long remainingDays;

    private LocalDateTime returnDate;

    @PositiveOrZero(message = "renewal count should not be negative")
    private Integer renewalCount;

    @PositiveOrZero(message = "maximum renewals should not be negative")
    private Integer maxRenewals;

    @PositiveOrZero(message = "Fine amount should not be negative")
    private BigDecimal fineAmount;

    @PositiveOrZero(message = "Fine paid should not be negative")
    private BigDecimal finePaid;

    private String notes;

    private Boolean isOverdue = false;

    @PositiveOrZero(message = "Overdue Days should not be null")
    private Integer overdueDays;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
