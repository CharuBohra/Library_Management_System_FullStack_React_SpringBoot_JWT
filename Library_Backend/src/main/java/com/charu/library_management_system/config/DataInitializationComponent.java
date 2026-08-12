package com.charu.library_management_system.config;

import com.charu.library_management_system.enums.UserRole;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private void initializeAdminUser(){
        String adminEmail = "adminlibrary@gmail.com";

        if(!userRepository.existsByEmail(adminEmail))
        {
            User user = User.builder()
                    .fullName("Admin")
                    .password(passwordEncoder.encode("Admin@123"))
                    .email(adminEmail)
                    .phone("9821345672")
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.save(user);
        }
    }
    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }
}
