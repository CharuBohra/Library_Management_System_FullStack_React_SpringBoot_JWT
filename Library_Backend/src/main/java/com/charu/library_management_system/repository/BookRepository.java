package com.charu.library_management_system.repository;

import com.charu.library_management_system.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByIsbn(String isbn);

    Boolean existsByIsbn(String isbn);

    @Query("select b from Book b where "+
            "(:searchTerm is null OR "+
                "lower(title) like lower(concat('%', :searchTerm, '%')) OR "+
                "lower(isbn) like lower(concat('%', :searchTerm, '%')) OR "+
                "lower(author) like lower(concat('%', :searchTerm, '%'))) AND " +
            "(:genreId is null or b.genre.id = :genreId) AND "+
            "(:availableOnly = false or b.availableCopies > 0) AND "+
            "b.active = true")
    Page<Book> searchBooksWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("genreId") Long genreId,
            @Param("availableOnly") Boolean availableOnly,
            Pageable pageable
    );

    Long countActiveByTrue();

    @Query("select count(b) from Book b where b.availableCopies>0 and b.active=true")
    Long countAvailableBooks();
}
