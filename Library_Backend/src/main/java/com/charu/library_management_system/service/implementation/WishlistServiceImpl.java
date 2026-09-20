package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.WishlistDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.exception.BookAlreadyInWishlistException;
import com.charu.library_management_system.exception.BookNotFoundException;
import com.charu.library_management_system.exception.UserNotFoundException;
import com.charu.library_management_system.exception.WishlistNotFoundException;
import com.charu.library_management_system.mapper.WishlistMapper;
import com.charu.library_management_system.models.Book;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.models.Wishlist;
import com.charu.library_management_system.repository.BookRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.repository.WishlistRepository;
import com.charu.library_management_system.service.UserService;
import com.charu.library_management_system.service.WishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserService userService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final WishlistMapper wishlistMapper;

    @Override
    @Transactional
    public WishlistDTO addToWishlist(Long bookId, String notes) {
        UserDTO userDTO = userService.getCurrentUser();
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookNotFoundException("Book not found for id "+bookId));

        if(wishlistRepository.existsByUserIdAndBookId(userDTO.getId(),bookId))
        {
            throw new BookAlreadyInWishlistException("Book is already in the user's wishlist");
        }

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .book(book)
                .addedAt(LocalDateTime.now())
                .notes(notes)
                .build();

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return wishlistMapper.toDTO(savedWishlist);
    }

    @Override
    public void removeFromWishlist(Long bookId) {
        UserDTO userDTO = userService.getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserIdAndBookId(userDTO.getId(),bookId);

        if(wishlist==null)
        {
            throw new WishlistNotFoundException("Wishlist not there for book for this current user");
        }

        wishlistRepository.delete(wishlist);
    }

    @Override
    public PageResponseDTO<WishlistDTO> getMyWishlist(int page, int size) {

        UserDTO user = userService.getCurrentUser();

        Pageable pageable = PageRequest.of(page , size, Sort.by("addedAt").descending());

        Page<Wishlist> wishlistPage = wishlistRepository.findByUserId(user.getId(),pageable);

        return convertToPageResponse(wishlistPage);
    }

    private PageResponseDTO<WishlistDTO> convertToPageResponse(Page<Wishlist> wishlistPage)
    {
        List<WishlistDTO> wishlistDTOS = wishlistPage.getContent()
                .stream()
                .map(wishlistMapper::toDTO)
                .toList();

        PageResponseDTO<WishlistDTO> pageResponse = PageResponseDTO.<WishlistDTO>builder()
                .content(wishlistDTOS)
                .pageNumber(wishlistPage.getNumber())
                .pageSize(wishlistPage.getSize())
                .totalPages(wishlistPage.getTotalPages())
                .totalElements(wishlistPage.getTotalElements())
                .first(wishlistPage.isFirst())
                .last(wishlistPage.isLast())
                .empty(wishlistPage.isEmpty())
                .build();

        return pageResponse;
    }
}
