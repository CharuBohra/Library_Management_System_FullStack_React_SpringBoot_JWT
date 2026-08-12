package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.LoginRequestDTO;
import com.charu.library_management_system.dto.requestDTO.ResetPasswordRequestDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.AuthResponse;
import com.charu.library_management_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/forgot-password")
    ResponseEntity<ApiResponse> forgotPassword(@RequestParam String email)
    {
        authService.createResetPasswordToken(email);
        ApiResponse apiResponse = new ApiResponse("Password reset link sent successfully",true);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/reset-password")
    ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO)
    {
        authService.resetPassword(resetPasswordRequestDTO.getToken(), resetPasswordRequestDTO.getNewPassword());
        ApiResponse apiResponse = new ApiResponse("Password reset successfully",true);
        return ResponseEntity.ok(apiResponse);
    }
}
