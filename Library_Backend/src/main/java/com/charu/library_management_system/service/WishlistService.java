package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.WishlistDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface WishlistService {

    WishlistDTO addToWishlist(Long bookId, String notes);

    void removeFromWishlist(Long bookId);

    PageResponseDTO<WishlistDTO> getMyWishlist(int page , int size);
}
