package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.requestDTO.BookSearchRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;

import java.util.List;

public interface BookService {
    BookDTO createBook (BookDTO bookDTO);

    List<BookDTO> createBooksBulk(List<BookDTO> bookDTOS);

    BookDTO getBookById(Long bookId);

    BookDTO getBookByISBN(String isbn);

    BookDTO updateBook(BookDTO bookDTO);

    void deleteBook(Long bookId);

    void hardDeleteBook(Long bookId);

    PageResponseDTO<BookDTO> searchBooksWithFilters(BookSearchRequestDTO bookSearchRequestDTO);

    Long getTotalActiveBooks();

    Long getTotalAvailableBooks();
}
