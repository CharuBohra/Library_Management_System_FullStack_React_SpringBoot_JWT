package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.ReservationDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationRequestDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationSearchRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.enums.ReservationStatus;
import com.charu.library_management_system.exception.BookNotFoundException;
import com.charu.library_management_system.exception.UserNotFoundException;
import com.charu.library_management_system.mapper.ReservationMapper;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.Book;
import com.charu.library_management_system.models.BookLoan;
import com.charu.library_management_system.models.Reservation;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.BookLoanRepository;
import com.charu.library_management_system.repository.BookRepository;
import com.charu.library_management_system.repository.ReservationRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.service.BookService;
import com.charu.library_management_system.service.ReservationService;
import com.charu.library_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final BookLoanRepository bookLoanRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final UserService userService;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    private int MAX_RESERVATIONS = 5;


    @Override
    public ReservationDTO createReservation(ReservationRequestDTO reservationRequest) {
        UserDTO userDTO = userService.getCurrentUser();
        return createReservationForUser(userDTO.getId(),reservationRequest);
    }

    @Override
    public ReservationDTO createReservationForUser(Long userId, ReservationRequestDTO reservationRequest) {
        //Check if user already has loan
        boolean hasActiveLoan = bookLoanRepository.existsByUserIdAndBookIdAndStatus(
                userId,reservationRequest.getBookId(), BookLoanStatus.CHECKED_OUT);

        if(hasActiveLoan)
        {
            throw new UserAlreadyHasBookLoan("User already has this book loan with them");
        }

        //Validate user exist
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User not found for id "+ userId);

        //Validate Book exists
        Book book = bookRepository.findById(reservationRequest.getBookId())
                .orElseThrow(()-> new BookNotFoundException("Book not found for id "+reservationRequest.getBookId()));

        //check if user already has Active reservation
        boolean hasActiveReservation = reservationRepository.hasActiveReservation(userId,reservationRequest.getBookId());
        if(hasActiveReservation)
        {
            throw new UserAlreadyHasReservation("User already has reservation for this book");
        }

        //check if book is available
        if (book.getAvailableCopies() > 0) {
            throw new BookAlreadyAvailableException(
                    "Book is currently available. You can borrow it directly."
            );
        }

        //check user reservation limit
        long activeReservations = reservationRepository.countActiveReservationsByUser(userId);
        if(activeReservations >= MAX_RESERVATIONS)
        {
            throw new MaxReservationLimitException("You have already reached "+ MAX_RESERVATIONS+" reservations limit");
        }

        //create reservation
        Reservation reservation = Reservation.builder()
                .user(user)
                .book(book)
                .status(ReservationStatus.PENDING)
                .reservedAt(LocalDateTime.now())
                .notificationsSent(false)
                .notes(reservationRequest.getNotes())
                .build();

        long pendingReservations = reservationRepository.countPendingReservationsByBook(reservationRequest.getBookId());

        reservation.setQueuePosition((int) pendingReservations +1);

        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toDTO(savedReservation);
    }

    @Override
    public ReservationDTO cancelReservation(Long reservationId) {
        return null;
    }

    @Override
    public ReservationDTO fulfillReservation(Long reservationId) {
        return null;
    }

    @Override
    public PageResponseDTO<ReservationDTO> getMyReservations(ReservationSearchRequestDTO reservationSearchRequest) {
        return null;
    }

    @Override
    public PageResponseDTO<ReservationDTO> searchReservations(ReservationSearchRequestDTO reservationSearchRequest) {
        return null;
    }
}
