package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.WishlistDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.WishlistService;

public class WishlistServiceImpl implements WishlistService {
    @Override
    public WishlistDTO addToWishlist(Long bookId, String notes) {
        return null;
    }

    @Override
    public void removeFromWishList(Long bookId) {

    }

    @Override
    public PageResponseDTO<WishlistDTO> getMyWishlist(int page, int size) {
        return null;
    }
}
