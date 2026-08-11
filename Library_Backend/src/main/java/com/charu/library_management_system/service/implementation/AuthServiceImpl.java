package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.LoginRequestDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.AuthResponse;
import com.charu.library_management_system.enums.AuthProvider;
import com.charu.library_management_system.enums.UserRole;
import com.charu.library_management_system.exception.UserExistsException;
import com.charu.library_management_system.exception.UserNotFoundException;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.security.CustomUserDetails;
import com.charu.library_management_system.security.JwtService;
import com.charu.library_management_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
        User user = userRepository.findUserByEmail(email)
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
    public void createResetPasswordToken(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
