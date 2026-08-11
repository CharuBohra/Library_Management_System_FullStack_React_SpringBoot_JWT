package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.LoginRequestDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.AuthResponse;
import com.charu.library_management_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserDTO userReq)
    {
        ApiResponse apiResponse = authService.signup(userReq);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO)
    {
        AuthResponse authResponse = authService.login(loginRequestDTO);
        return ResponseEntity.ok(authResponse);
    }
}
