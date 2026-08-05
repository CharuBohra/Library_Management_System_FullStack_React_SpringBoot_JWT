package com.charu.library_management_system.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "ISBN is mandatory for books")
    @Size(max = 20, message = "ISBN cannot exceed 20 characters")
    private String isbn;

    @NotBlank(message = "Book title is mandatory")
    @Size(min = 1,max = 255 , message = "Title length should be in between 1-255 characters only")
    private String title;

    @NotBlank(message = "Book author is mandatory")
    @Size(min = 1, max = 255 , message = "Author length should be in between 1-255 characters only")
    private String author;

    @NotNull(message = "genre Id is mandatory")
    private Long genreId;

    private String genreCode;

    private String genreName;

    @Size(max = 100 , message = "publisher should not exceed 100 characters")
    private String publisher;

    private LocalDate publicationDate;

    @Size(max = 20, message = "language should not exceed 20 characters")
    private String language;

    @Min(value = 1, message = "Book should have at least 1 page")
    @Max(value = 50000 , message = "Book should not have more than 50000 pages")
    private Integer pages;

    @Size(max=2000, message = "Book description should not exceed 2000 characters")
    private String description;

    @NotNull(message = "Total Copies of books is required")
    @Min(0)
    private Integer totalCopies;

    @NotNull(message = "Available Copies is mandatory")
    @Min(value = 0,message = "Available copies cannot have negative values")
    private Integer availableCopies;

    @DecimalMin(value = "0.00",message = "Price of the book cannot be negative")
    @Digits(integer = 8 , fraction = 2,message = "price must have only 2 decimal values and 8 integer places")
    private BigDecimal price;

    @Size(max = 500, message = "image url should not exceed 500 characters")
    private String coverImageUrl;

    private Boolean active = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @AssertTrue(message = "Available copies should not be greater than total copies")
    public boolean isAvailableCopiesValid()
    {
        if(totalCopies== null || availableCopies==null)
        {
            return true;
        }
        return availableCopies<=totalCopies;
    }

}
