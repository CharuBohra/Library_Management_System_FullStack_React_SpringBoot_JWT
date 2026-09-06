package com.charu.library_management_system.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaiveFineRequestDTO {

    @NotNull(message = "Fine ID is required to waive the fine for user")
    private Long fineId;

    @Size(max = 500, message = "reason should not exceed 500 characters")
    private String reason;
}
