package com.charu.library_management_system.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {

    private Long Id;

    @NotBlank(message = "Genre code is required ")
    private String code;

    @NotBlank(message = "Genre name is required")
    private String name;

    @Size(max = 500 , message = "description must be less than 500 characters")
    private String description;

    @Min(value = 0 ,message = "displayOrder cannot be negative")
    private Integer displayOrder;

    private Boolean active;

    private Long parentGenreId;

    private String parentGenreName;

    private List<GenreDTO> subGenres;

    private Long BookCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
