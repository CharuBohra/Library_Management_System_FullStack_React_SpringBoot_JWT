package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.LoginRequestDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.AuthResponse;
import com.charu.library_management_system.enums.AuthProvider;
import com.charu.library_management_system.enums.UserRole;
import com.charu.library_management_system.exception.ResetTokenExpiredException;
import com.charu.library_management_system.exception.ResetTokenNotFoundException;
import com.charu.library_management_system.exception.UserExistsException;
import com.charu.library_management_system.exception.UserNotFoundException;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.PasswordResetToken;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.PasswordResetTokenRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.security.CustomUserDetails;
import com.charu.library_management_system.security.JwtService;
import com.charu.library_management_system.service.AuthService;
import com.charu.library_management_system.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Override
    public ApiResponse signup(UserDTO req) {
        if(userRepository.existsByEmail(req.getEmail())){
            throw new UserExistsException("User already exists with email "+ req.getEmail());
        }

        User user = userMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setAuthProvider(AuthProvider.LOCAL);

        User savedUser = userRepository.save(user);

        ApiResponse apiResponse = new ApiResponse("User Registered Successfully", true);

        return apiResponse;
    }

    @Override
    public AuthResponse login(LoginRequestDTO req) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                req.getEmail(),
                                req.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email "+email));

        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

        UserDTO userDTO = userMapper.toDTO(user);

        String jwt = jwtService.generateToken(userDetails);

        AuthResponse authResponse =
                AuthResponse.builder()
                        .jwt(jwt)
                        .title("Login Successfull")
                        .message("User logged in successfully")
                        .user(userDTO)
                        .build();

        return authResponse;
}

    @Override
    @Transactional
    public void createResetPasswordToken(String email) {
        String frontendUrl = "http://localhost:5173/";

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User does not exist with email "+email));

        String rstToken = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
               .token(rstToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        PasswordResetToken savedToken = passwordResetTokenRepository.save(passwordResetToken);

        String resetToken = frontendUrl+rstToken;

        String subject = "Password Reset Link";

        String body = "Click the following link to reset your password. " +
                "This link is valid for 5 minutes:\n" +
                resetToken;

        emailService.sendEmail(user.getEmail(),subject,body);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(()->new ResetTokenNotFoundException("Reset token not found"));

        if(resetToken.isExpired()){
            throw new ResetTokenExpiredException("Reset token has expired");
        }

        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));

        User savedUser = userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}
