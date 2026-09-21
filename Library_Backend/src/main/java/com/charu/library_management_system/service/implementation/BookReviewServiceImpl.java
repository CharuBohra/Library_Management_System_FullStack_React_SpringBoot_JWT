package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookReviewDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.CreateViewRequestDTO;
import com.charu.library_management_system.dto.requestDTO.UpdateViewRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.exception.*;
import com.charu.library_management_system.mapper.BookReviewMapper;
import com.charu.library_management_system.models.*;
import com.charu.library_management_system.repository.BookLoanRepository;
import com.charu.library_management_system.repository.BookRepository;
import com.charu.library_management_system.repository.BookReviewRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.service.BookReviewService;
import com.charu.library_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class BookReviewServiceImpl implements BookReviewService {

    private final UserService userService;
    private final BookReviewRepository bookReviewRepository;
    private final BookReviewMapper bookReviewMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookLoanRepository bookLoanRepository;

    @Override
    @Transactional
    public BookReviewDTO createBookReview(CreateViewRequestDTO createViewRequest) {
        UserDTO userDTO = userService.getCurrentUser();

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(()-> new UserNotFoundException("User not found with id "+userDTO.getId()));

        Book book = bookRepository.findById(createViewRequest.getBookId())
                .orElseThrow(()-> new BookNotFoundException("Book not found with id "+createViewRequest.getBookId()));

        if(bookReviewRepository.existsByUserIdAndBookId(user.getId(),createViewRequest.getBookId()))
        {
            throw new BookAlreadyReviewedException("You have already reviewed the book");
        }

        boolean hasReadBook = hasReadBook(user.getId(),book.getId());
        if(!hasReadBook)
        {
            throw new BookNotEligibleForReviewException("You can review a book only after borrowing and returning it.");
        }

        BookReview bookReview = BookReview.builder()
                .user(user)
                .book(book)
                .rating(createViewRequest.getRating())
                .reviewText(createViewRequest.getReviewText())
                .title(createViewRequest.getTitle())
                .build();

        BookReview savedBookReview = bookReviewRepository.save(bookReview);

        return bookReviewMapper.toDTO(savedBookReview);
    }

    @Override
    @Transactional
    public BookReviewDTO updateBookReview(Long reviewId, UpdateViewRequestDTO updateViewRequest) {
        UserDTO userDTO = userService.getCurrentUser();

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(()-> new UserNotFoundException("User not found with id "+userDTO.getId()));

        BookReview bookReview = bookReviewRepository.findById(reviewId)
                .orElseThrow(()-> new BookReviewNotFoundException("Book review not found"));

        if(!bookReview.getUser().getId().equals(user.getId()))
        {
            throw new ReviewNotOwnedByCurrentUserException("You can only modify your own review.");
        }

        bookReview.setRating(updateViewRequest.getRating());
        bookReview.setReviewText(updateViewRequest.getReviewText());
        bookReview.setTitle(updateViewRequest.getTitle());

        BookReview savedBookReview = bookReviewRepository.save(bookReview);

        return bookReviewMapper.toDTO(savedBookReview);
    }

    @Override
    @Transactional
    public void deleteBookReview(Long reviewId) {
        UserDTO userDTO = userService.getCurrentUser();

        BookReview bookReview = bookReviewRepository.findById(reviewId)
                .orElseThrow(()-> new BookReviewNotFoundException("Book review not found"));

        if(!bookReview.getUser().getId().equals(userDTO.getId()))
        {
            throw new ReviewNotOwnedByCurrentUserException("You can only delete your own review.");
        }

        bookReviewRepository.delete(bookReview);
    }

    @Override
    public PageResponseDTO<BookReviewDTO> getReviewsByBookId(Long bookId, int page, int size) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException("Book not found with id "+bookId));

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<BookReview> bookReviewPage = bookReviewRepository.findByBook(book,pageable);

        return convertToPageResponse(bookReviewPage);
    }

    private PageResponseDTO<BookReviewDTO> convertToPageResponse(Page<BookReview> bookReviewPage)
    {
        List<BookReviewDTO> bookReviewDTOs = bookReviewPage.getContent()
                .stream()
                .map(bookReviewMapper::toDTO)
                .toList();

        PageResponseDTO<BookReviewDTO> pageResponse = PageResponseDTO.<BookReviewDTO>builder()
                .content(bookReviewDTOs)
                .pageNumber(bookReviewPage.getNumber())
                .pageSize(bookReviewPage.getSize())
                .totalPages(bookReviewPage.getTotalPages())
                .totalElements(bookReviewPage.getTotalElements())
                .first(bookReviewPage.isFirst())
                .last(bookReviewPage.isLast())
                .empty(bookReviewPage.isEmpty())
                .build();

        return pageResponse;
    }

    private boolean hasReadBook(Long userId,Long bookId)
    {
        return bookLoanRepository.existsByUserIdAndBookIdAndStatus(userId,bookId,BookLoanStatus.RETURNED);
    }
}
