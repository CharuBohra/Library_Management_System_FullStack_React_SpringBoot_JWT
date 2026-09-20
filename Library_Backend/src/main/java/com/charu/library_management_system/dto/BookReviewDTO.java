package com.charu.library_management_system.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewDTO {

    private Long id;

    private Long userId;

    private String userName;

    @NotNull(message = "Book Id is mandatory for Review")
    private Long bookId;

    private String bookTitle;

    @NotNull(message = "Rating is mandatory for review")
    @Min(value = 1,message = "Minimum rating for Book Review should not be less than 1")
    @Max(value = 5,message = "Maximum rating for Book Review should not exceed 5")
    private Integer rating;

    @NotBlank(message = "Review Text is mandatory for Review")
    @Size(min = 10,max = 2000, message = "Review Text must be between 10 and 2000 characters")
    private String reviewText;

    @Size(max = 200, message = "The title for the book review should not exceed 200 characters")
    private String title;

    private Boolean isVerifiedReader;

    private Boolean isActive;

    private Integer helpfulCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
