package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.BookReviewDTO;
import com.charu.library_management_system.dto.requestDTO.CreateViewRequestDTO;
import com.charu.library_management_system.dto.requestDTO.UpdateViewRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BookReviewService {

    BookReviewDTO createBookReview (CreateViewRequestDTO createViewRequest);

    BookReviewDTO updateBookReview (Long reviewId ,UpdateViewRequestDTO updateViewRequest);

    void deleteBookReview (Long reviewId);

    PageResponseDTO<BookReviewDTO> getReviewsByBookId(Long bookId , int page , int size);
}
