package com.charu.library_management_system.dto.requestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSearchRequestDTO {
    private String title;

    private Long genreId;

    private Boolean availableOnly;

    private Integer page=0;

    private Integer pageSize = 20;

    private String sortBy = "createdAt";

    private String sortDirection = "DESC";
}
