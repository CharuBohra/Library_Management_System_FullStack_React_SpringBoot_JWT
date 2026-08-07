package com.charu.library_management_system.dto.responseDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookStatsResponse {
    private Long totalActiveBooks;

    private Long totalAvailableBooks;
}
