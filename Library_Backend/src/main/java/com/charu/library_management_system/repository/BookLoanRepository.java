package com.charu.library_management_system.repository;

import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.models.BookLoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BookLoanRepository extends JpaRepository<BookLoan,Long> {

    Page<BookLoan> findByStatus(BookLoanStatus status , Pageable pageable);
    Page<BookLoan> findByUserId(Long userId ,Pageable pageable);
    Page<BookLoan> findByUserIdAndStatus(Long userId, BookLoanStatus status, Pageable pageable);
    Page<BookLoan> findByBookId(Long bookId, Pageable pageable);

    @Query("SELECT case when count(bl)>0 then true else false end from BookLoan bl "+
    "where bl.user.id = :userId and bl.book.id = :bookId "+
    "and (bl.status = 'CHECKED_OUT' OR bl.status = 'OVERDUE')")
    boolean hasActiveCheckout(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query("SELECT count(bl) from BookLoan bl where bl.user.id=:userId "+
    "and (bl.status = 'CHECKED_OUT' OR bl.status = 'OVERDUE')")
    long countActiveBookLoansByUser(@Param("userId") Long userId);

    @Query("SELECT count(bl) from BookLoan bl where bl.user.id=:userId "+
            "and bl.status = 'OVERDUE' ")
    long countOverdueBookLoansByUser(@Param("userId") Long userId);

    @Query("SELECT bl from BookLoan bl WHERE bl.dueDate<:currentDate "+
    "AND (bl.status = 'CHECKED_OUT' OR bl.status = 'OVERDUE')")
    Page<BookLoan> findOverdueBookLoans(@Param("currentDate")LocalDateTime currentDate, Pageable pageable);

    @Query("SELECT bl from BookLoan bl WHERE bl.checkoutDate BETWEEN :startDate AND :endDate")
    Page<BookLoan> findBookLoansByDateRange(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate,
                                            Pageable pageable);
}
