package com.charu.library_management_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne
    private User user;

    private LocalDateTime expiryDate;

    public boolean isExpired(){
        return expiryDate==null || expiryDate.isBefore(LocalDateTime.now());
    }
}
