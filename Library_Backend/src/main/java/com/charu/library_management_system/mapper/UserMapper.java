package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "password" , ignore = true)
    UserDTO toDTO(User user);

    @Mapping(target = "password",ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "role",ignore = true)
    @Mapping(target = "authProvider", ignore = true)
    @Mapping(target = "googleId", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin",ignore = true)
    User toEntity(UserDTO userDTO);
}
