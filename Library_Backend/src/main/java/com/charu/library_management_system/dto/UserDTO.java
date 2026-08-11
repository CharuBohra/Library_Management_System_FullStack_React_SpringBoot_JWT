package com.charu.library_management_system.dto;

import com.charu.library_management_system.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Full name of user is required")
    private String fullName;

    private UserRole role;

    private String phone;

    private LocalDateTime lastLogin;
}
