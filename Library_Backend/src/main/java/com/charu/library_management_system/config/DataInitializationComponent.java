package com.charu.library_management_system.config;

import com.charu.library_management_system.enums.UserRole;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void initializeAdminUser(){

        if(!userRepository.existsByEmail(adminEmail))
        {
            User user = User.builder()
                    .fullName("Admin")
                    .password(passwordEncoder.encode(adminPassword))
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
