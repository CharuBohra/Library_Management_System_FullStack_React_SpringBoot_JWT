package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.LoginRequestDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.AuthResponse;

public interface AuthService {

    ApiResponse signup(UserDTO req);
    AuthResponse login(LoginRequestDTO req);
    void createResetPasswordToken(String email);
    void resetPassword(String token,String newPassword);
}
