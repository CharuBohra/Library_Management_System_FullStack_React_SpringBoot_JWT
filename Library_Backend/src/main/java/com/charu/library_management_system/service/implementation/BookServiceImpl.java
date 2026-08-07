package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.requestDTO.BookSearchRequestDTO;
import com.charu.library_management_system.dto.requestDTO.UpdateBookRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.exception.BookNotFoundException;
import com.charu.library_management_system.exception.DuplicateIsbnException;
import com.charu.library_management_system.exception.GenreNotFoundException;
import com.charu.library_management_system.mapper.BookMapper;
import com.charu.library_management_system.models.Book;
import com.charu.library_management_system.models.Genre;
import com.charu.library_management_system.repository.BookRepository;
import com.charu.library_management_system.repository.GenreRepository;
import com.charu.library_management_system.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        if(bookRepository.existsByIsbn(bookDTO.getIsbn())){
            throw new DuplicateIsbnException("Book already exists with isbn "+bookDTO.getIsbn());
        }

        Genre genre = genreRepository.findById(bookDTO.getGenreId())
                .orElseThrow(()->new GenreNotFoundException("Genre does not exist for id "+bookDTO.getGenreId()));

        Book book = bookMapper.toEntity(bookDTO);

        book.setGenre(genre);

        Book savedBook = bookRepository.save(book);

        return bookMapper.toDTO(savedBook);
    }

    @Override
    @Transactional
    public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOS) {
        List<BookDTO> createdBooks = new ArrayList<>();

        for(BookDTO bookDTO : bookDTOS){
            BookDTO book = createBook(bookDTO);
            createdBooks.add(book);
        }

        return createdBooks;
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookNotFoundException("Book not found with id "+bookId));

        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO getBookByISBN(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(()->new BookNotFoundException("Book not found with isbn number "+isbn));

        return bookMapper.toDTO(book);
    }

    @Override
    @Transactional
    public BookDTO updateBook(Long id, UpdateBookRequestDTO updateBookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()->new BookNotFoundException("Book does not exist with id "+id));

        bookMapper.updateEntityFromDTO(updateBookDTO,book);

        if(updateBookDTO.getGenreId()!=null)
        {
            Genre genre = genreRepository.findById(updateBookDTO.getGenreId())
                    .orElseThrow(()->new GenreNotFoundException("Genre not found for id "+updateBookDTO.getGenreId()));
            book.setGenre(genre);
        }

        Book updatedBook = bookRepository.save(book);

        return bookMapper.toDTO(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookNotFoundException("Book does not exist with id "+bookId));

        book.setActive(false);
    }

    @Override
    @Transactional
    public void hardDeleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookNotFoundException("Book does not exist with id "+bookId));

        bookRepository.deleteById(bookId);
    }

    @Override
    public PageResponseDTO<BookDTO> searchBooksWithFilters(BookSearchRequestDTO bookSearchRequestDTO) {
        Pageable pageable = createPageable(
                bookSearchRequestDTO.getPage(),
                bookSearchRequestDTO.getPageSize(),
                bookSearchRequestDTO.getSortBy(),
                bookSearchRequestDTO.getSortDirection()
        );

        Page<Book> bookPage = bookRepository.searchBooksWithFilters(bookSearchRequestDTO.getSearchTerm(),
                bookSearchRequestDTO.getGenreId(),
                Boolean.TRUE.equals(bookSearchRequestDTO.getAvailableOnly()),
                pageable);

        return convertToPageResponse(bookPage);
    }

    @Override
    public Long getTotalActiveBooks() {
        Long activeBookCount = bookRepository.countByActiveTrue();
        return activeBookCount;
    }

    @Override
    public Long getTotalAvailableBooks() {
        Long countAvailable = bookRepository.countAvailableBooks();
        return countAvailable;
    }

    private Pageable createPageable(int page , int pageSize , String sortBy , String sortDirection)
    {
        pageSize = Math.min(pageSize,10);
        pageSize = Math.max(pageSize,1);

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return PageRequest.of(page,pageSize,sort);
    }

    private PageResponseDTO<BookDTO> convertToPageResponse(Page<Book> bookPage)
    {
        List<BookDTO> bookDTOS = bookPage.getContent()
                .stream()
                .map(bookMapper::toDTO)
                .toList();

        PageResponseDTO<BookDTO> pageResponse = PageResponseDTO.<BookDTO>builder()
                .content(bookDTOS)
                .pageNumber(bookPage.getNumber())
                .pageSize(bookPage.getSize())
                .totalPages(bookPage.getTotalPages())
                .totalElements(bookPage.getTotalElements())
                .first(bookPage.isFirst())
                .last(bookPage.isLast())
                .empty(bookPage.isEmpty())
                .build();

        return pageResponse;
    }

}
