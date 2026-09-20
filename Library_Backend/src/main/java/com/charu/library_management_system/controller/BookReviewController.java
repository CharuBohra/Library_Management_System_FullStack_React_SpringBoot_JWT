package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.BookReviewDTO;
import com.charu.library_management_system.dto.requestDTO.CreateViewRequestDTO;
import com.charu.library_management_system.dto.requestDTO.UpdateViewRequestDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.BookReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @PostMapping
    public ResponseEntity<BookReviewDTO> createReview(@Valid @RequestBody CreateViewRequestDTO createViewRequest)
    {
        BookReviewDTO bookReview = bookReviewService.createBookReview(createViewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookReview);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<BookReviewDTO> updateReview(@PathVariable("reviewId") Long reviewId,
                                                          @Valid @RequestBody UpdateViewRequestDTO updateViewRequest)
    {
        BookReviewDTO bookReview = bookReviewService.updateBookReview(reviewId,updateViewRequest);
        return ResponseEntity.ok(bookReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable("reviewId") Long reviewId)
    {
        bookReviewService.deleteBookReview(reviewId);
        ApiResponse apiResponse = new ApiResponse("Review deleted successfully",true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponseDTO<BookReviewDTO>> getBookReviews(@PathVariable("bookId") Long bookId,
                                                                         @RequestParam(required = false,defaultValue = "0") int page,
                                                                         @RequestParam(required = false,defaultValue = "10") int size)
    {
        PageResponseDTO<BookReviewDTO> bookReviews = bookReviewService.getReviewsByBookId(bookId,page,size);
        return ResponseEntity.ok(bookReviews);
    }
}
