package com.charu.library_management_system.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WishlistDTO {

    private Long id;

    private Long userId;

    private String userFullName;

    private BookDTO book;

    private LocalDateTime addedAt;

    @Size(max = 500,message = "Notes length should not exceed 500 characters")
    private String notes;
}
