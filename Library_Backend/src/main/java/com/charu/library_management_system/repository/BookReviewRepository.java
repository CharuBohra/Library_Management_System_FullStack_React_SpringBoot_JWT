package com.charu.library_management_system.repository;

import com.charu.library_management_system.models.Book;
import com.charu.library_management_system.models.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview,Long> {

    Page<BookReview> findByBook(Book book ,Pageable pageable);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
