package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.requestDTO.BookSearchRequestDTO;
import com.charu.library_management_system.dto.requestDTO.UpdateBookRequestDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.BookStatsResponse;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO)
    {
        BookDTO book = bookService.createBook(bookDTO);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/create/bulk")
    public ResponseEntity<List<BookDTO>> addBooksBulk(@Valid @RequestBody List<BookDTO> bookDTOS)
    {
        List<BookDTO> books = bookService.createBooksBulk(bookDTOS);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id){
        BookDTO bookDTO = bookService.getBookById(id);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByISBN(@PathVariable("isbn") String isbn)
    {
        BookDTO bookDTO = bookService.getBookByISBN(isbn);
        return ResponseEntity.ok(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id,@Valid @RequestBody UpdateBookRequestDTO updateBookDTO)
    {
        BookDTO bookDTO = bookService.updateBook(id,updateBookDTO);
        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable("id") Long id)
    {
        bookService.deleteBook(id);
        ApiResponse apiResponse = new ApiResponse("Book Deleted - Soft Delete",true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<ApiResponse> hardDeleteBook(@PathVariable("id") Long id)
    {
        bookService.hardDeleteBook(id);
        ApiResponse apiResponse = new ApiResponse("Book Deleted - Hard Delete",true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<BookDTO>> searchBooks(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false , defaultValue = "false") Boolean availableOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ){
        BookSearchRequestDTO bookSearchRequestDTO = new BookSearchRequestDTO();
        bookSearchRequestDTO.setGenreId(genreId);
        bookSearchRequestDTO.setAvailableOnly(availableOnly);
        bookSearchRequestDTO.setPage(page);
        bookSearchRequestDTO.setPageSize(pageSize);
        bookSearchRequestDTO.setSortBy(sortBy);
        bookSearchRequestDTO.setSortDirection(sortDirection);

        PageResponseDTO<BookDTO> searchBooks = bookService.searchBooksWithFilters(bookSearchRequestDTO);
        return ResponseEntity.ok(searchBooks);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponseDTO<BookDTO>> advancedSearch(@Valid @RequestBody BookSearchRequestDTO bookSearchRequestDTO)
    {
        PageResponseDTO<BookDTO> searchBook = bookService.searchBooksWithFilters(bookSearchRequestDTO);
        return ResponseEntity.ok(searchBook);
    }

    @GetMapping("/stats")
    public ResponseEntity<BookStatsResponse> getBookStats(){
        long totalActiveBooks = bookService.getTotalActiveBooks();
        long totalAvailableBooks = bookService.getTotalAvailableBooks();

        BookStatsResponse bookStats = new BookStatsResponse(totalActiveBooks,totalAvailableBooks);
        return ResponseEntity.ok(bookStats);
    }
}
