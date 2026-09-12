package com.charu.library_management_system.repository;

import com.charu.library_management_system.enums.ReservationStatus;
import com.charu.library_management_system.models.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r from Reservation r WHERE r.book.id=:bookId "+
    " AND r.status = 'PENDING' ORDER BY r.reservedAt ASC ")
    List<Reservation> findPendingReservationByBook(@Param("bookId") Long bookId);

    Optional<Reservation> findFirstByBookIdAndStatusOrderByReservedAtAsc(@Param("bookId") Long bookId ,ReservationStatus status);

    @Query("SELECT CASE WHEN count(r) >0 THEN true ELSE false END from Reservation r "+
    " WHERE r.user.id = :userId AND r.book.id= :bookId AND (r.status = 'PENDING' OR r.status = 'AVAILABLE')")
    boolean hasActiveReservation(@Param("userId") Long userId ,@Param("bookId") Long bookId);

    @Query("SELECT count(r) from Reservation r WHERE r.user.id = :userId AND "+
    "(r.status = 'PENDING' OR r.status = 'AVAILABLE')")
    long countActiveReservationsByUser(@Param("userId") Long userId);

    @Query("SELECT count(r) from Reservation r WHERE r.book.id = :bookId AND "+
            "(r.status = 'PENDING')")
    long countPendingReservationsByBook(@Param("bookId") Long bookId);

    @Query("SELECT r FROM Reservation r WHERE r.status = 'AVAILABLE' AND r.availableUntil IS NOT NULL AND r.availableUntil < :currentDateTime")
    List<Reservation> findExpiredReservations(@Param("currentDateTime")LocalDateTime currentDateTime);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId"+
      " AND r.book.id = :bookId AND r.status IN ('PENDING', 'AVAILABLE')")
    List<Reservation> findActiveReservationsByUserAndBook(@Param("userId") Long userId , @Param("bookId") Long bookId);

    @Query("SELECT r FROM Reservation r WHERE "+
    "(:userId IS NULL OR r.user.id = :userId ) AND "+
    "(:bookId IS NULL OR r.book.id = :bookId ) AND "+
    "(:status IS NULL OR r.status = :status ) AND "+
    "(:activeOnly = false OR (r.status ='PENDING' OR r.status='AVAILABLE'))")
    List<Reservation> searchReservationWithFilters(
            @Param("userId") Long userId,
            @Param("bookId") Long bookId,
            @Param("status")ReservationStatus status,
            @Param("activeOnly") Boolean activeOnly,
            Pageable pageable
    );
}
