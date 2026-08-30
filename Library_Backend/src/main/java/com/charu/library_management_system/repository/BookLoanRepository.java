package com.charu.library_management_system.repository;

import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.models.BookLoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLoanRepository extends JpaRepository<BookLoan,Long> {

    Page<BookLoan> findByStatus(BookLoanStatus status , Pageable pageable);
    Page<BookLoan> findByUserId(Long userId ,Pageable pageable);
    Page<BookLoan> findByUserIdAndStatus(Long userId, BookLoanStatus status, Pageable pageable);
    Page<BookLoan> findByBookId(Long bookId, Pageable pageable);


}
