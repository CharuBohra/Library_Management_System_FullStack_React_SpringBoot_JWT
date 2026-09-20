package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookReviewDTO;
import com.charu.library_management_system.dto.requestDTO.CreateViewRequestDTO;
import com.charu.library_management_system.dto.requestDTO.UpdateViewRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.BookReviewService;
import com.charu.library_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class BookReviewServiceImpl implements BookReviewService {

    private final UserService userService;

    @Override
    public BookReviewDTO createBookReview(CreateViewRequestDTO createViewRequest) {
        return null;
    }

    @Override
    public BookReviewDTO updateBookReview(Long reviewId, UpdateViewRequestDTO updateViewRequest) {
        return null;
    }

    @Override
    public void deleteBookReview(Long reviewId) {

    }

    @Override
    public PageResponseDTO<BookReviewDTO> getReviewsByBookId(Long bookId, int page, int size) {
        return null;
    }
}
