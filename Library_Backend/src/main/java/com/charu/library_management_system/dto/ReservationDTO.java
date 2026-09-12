package com.charu.library_management_system.dto;

import com.charu.library_management_system.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    private Long userId;

    private String userName;

    private String userEmail;

    @NotNull(message = "Book Id is required for reservation")
    private Long bookId;

    private String bookTitle;

    private String bookIsbn;

    private String bookAuthor;

    private Boolean isBookAvailable;

    private ReservationStatus status = ReservationStatus.PENDING;

    private LocalDateTime reservedAt;

    private LocalDateTime availableAt;

    private LocalDateTime availableUntil;

    private LocalDateTime fulfilledAt;

    private LocalDateTime cancelledAt;

    private Integer queuePosition;

    @Builder.Default
    private Boolean notificationsSent = false;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isExpired;

    private boolean canBeCancelled;

    private long hoursUntilExpiry;
}
