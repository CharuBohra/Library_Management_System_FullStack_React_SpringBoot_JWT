package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDTO getCurrentUser();

    List<UserDTO> getAllUsers();

    UserDTO findById(Long userId);
}
