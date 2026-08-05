package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.requestDTO.BookSearchRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOS) {
        return List.of();
    }

    @Override
    public List<BookDTO> searchBooks() {
        return List.of();
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        return null;
    }

    @Override
    public BookDTO getBookByISBN(String isbn) {
        return null;
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public void deleteBook(Long bookId) {

    }

    @Override
    public void hardDeleteBook(Long bookId) {

    }

    @Override
    public PageResponseDTO<BookDTO> searchBooksWithFilters(BookSearchRequestDTO bookSearchRequestDTO) {
        return null;
    }

    @Override
    public Long getTotalActiveBooks() {
        return 0L;
    }

    @Override
    public Long getTotalAvailableBooks() {
        return 0L;
    }
}
