package com.charu.library_management_system.dto.requestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateViewRequestDTO {

    @NotNull(message = "Book Id is mandatory")
    private Long bookId;

    @NotNull(message = "Rating is mandatory for review")
    @Min(value = 1,message = "Minimum rating for Book Review should not be less than 1")
    @Max(value = 5,message = "Maximum rating for Book Review should not exceed 5")
    private Integer rating;

    @NotBlank(message = "Review Text is mandatory for Review")
    @Size(min = 10,max = 2000, message = "Review Text must be between 10 and 2000 characters")
    private String reviewText;

    @Size(max = 200, message = "The title for the book review should not exceed 200 characters")
    private String title;
}
