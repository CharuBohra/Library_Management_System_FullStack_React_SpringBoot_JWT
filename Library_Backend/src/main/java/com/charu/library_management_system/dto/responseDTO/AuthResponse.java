package com.charu.library_management_system.dto.responseDTO;

import com.charu.library_management_system.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwt;
    private String title;
    private String message;
    private UserDTO user;
}
