package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.exception.UserNotFoundException;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email "+email));

        return userMapper.toDTO(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();

        return users;
    }

    @Override
    public UserDTO findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User not found for id "+userId));

        return userMapper.toDTO(user);
    }
}
