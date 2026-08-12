package com.charu.library_management_system.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    private Long id;

    private String token;

    private User user;

    private LocalDateTime expiryDate;

    public boolean isExpired(){
        return expiryDate==null || expiryDate.isBefore(LocalDateTime.now());
    }
}
