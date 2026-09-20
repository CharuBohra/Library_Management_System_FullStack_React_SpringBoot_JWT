package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.WishlistDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add/{bookId}")
    public ResponseEntity<WishlistDTO> addToWishlist(@PathVariable("bookId") Long bookId ,
                                                     @RequestParam(required = false) String notes)
    {
        WishlistDTO wishlistDTO = wishlistService.addToWishlist(bookId,notes);
        return ResponseEntity.ok(wishlistDTO);
    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<ApiResponse> removeFromWishlist(@PathVariable("bookId") Long bookId)
    {
        wishlistService.removeFromWishlist(bookId);
        ApiResponse response = new ApiResponse("Book removed from Wishlist successfully",true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<PageResponseDTO<WishlistDTO>> getMyWishlist(@RequestParam(required = false,defaultValue = "0") int page,
                                                                      @RequestParam(required = false,defaultValue = "10") int size)
    {
        PageResponseDTO<WishlistDTO> myWishlist = wishlistService.getMyWishlist(page,size);
        return ResponseEntity.ok(myWishlist);
    }
}
