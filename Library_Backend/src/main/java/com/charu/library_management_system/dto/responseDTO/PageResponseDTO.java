package com.charu.library_management_system.dto.responseDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponseDTO<T>{
    private List<T> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    private boolean first;

    private boolean last;

    private boolean empty;
}
