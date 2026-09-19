package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.WishlistDTO;
import com.charu.library_management_system.models.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "userFullName",source = "user.fullName")
    @Mapping(target = "book", source = "book")
    WishlistDTO toDTO(Wishlist wishlist);

    @Mapping(target = "id",source = "id")
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "book",ignore = true)
    Wishlist toEntity(WishlistDTO wishlistDTO);
}
