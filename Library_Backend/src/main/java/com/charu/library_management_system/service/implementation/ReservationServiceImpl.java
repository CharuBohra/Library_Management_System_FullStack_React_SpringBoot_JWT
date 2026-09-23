package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.ReservationDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.CheckoutBookRequestDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationRequestDTO;
import com.charu.library_management_system.dto.requestDTO.ReservationSearchRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.enums.ReservationStatus;
import com.charu.library_management_system.enums.UserRole;
import com.charu.library_management_system.exception.*;
import com.charu.library_management_system.mapper.ReservationMapper;
import com.charu.library_management_system.models.Book;
import com.charu.library_management_system.models.Reservation;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.BookLoanRepository;
import com.charu.library_management_system.repository.BookRepository;
import com.charu.library_management_system.repository.ReservationRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.service.BookLoanService;
import com.charu.library_management_system.service.ReservationService;
import com.charu.library_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final BookLoanRepository bookLoanRepository;
    private final BookRepository bookRepository;
    private final BookLoanService bookLoanService;
    private final UserService userService;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    private static final int MAX_RESERVATIONS = 5;


    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationRequestDTO reservationRequest) {
        UserDTO userDTO = userService.getCurrentUser();
        return createReservationForUser(userDTO.getId(),reservationRequest);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ReservationDTO createReservationForUser(Long userId, ReservationRequestDTO reservationRequest) {
        //Check if user already has loan
        boolean hasActiveLoan = bookLoanRepository.existsByUserIdAndBookIdAndStatus(
                userId,reservationRequest.getBookId(), BookLoanStatus.CHECKED_OUT);

        if(hasActiveLoan)
        {
            throw new UserAlreadyHasBookLoanException("User already has this book loan with them");
        }

        //Validate user exist
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User not found for id "+ userId));

        //Validate Book exists
        Book book = bookRepository.findById(reservationRequest.getBookId())
                .orElseThrow(()-> new BookNotFoundException("Book not found for id "+reservationRequest.getBookId()));

        //check if user already has Active reservation
        boolean hasActiveReservation = reservationRepository.hasActiveReservation(userId,reservationRequest.getBookId());
        if(hasActiveReservation)
        {
            throw new UserAlreadyHasReservationException("User already has reservation for this book");
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
            throw new MaxReservationLimitException("You have already reserved "+ MAX_RESERVATIONS+" times");
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
    @Transactional
    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new ReservationNotFoundException("Reservation not found with id "+reservationId));

        UserDTO user = userService.getCurrentUser();

        if(!user.getId().equals(reservation.getUser().getId()) && !user.getRole().equals(UserRole.ADMIN))
        {
            throw new AccessDeniedException("You are not allowed to do this reservation");
        }
        if(!reservation.canBeCancelled())
        {
            throw new ReservationCannotBeCancelledException("Reservation cannot be cancelled");
        }

        Integer cancelledPosition = reservation.getQueuePosition();

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setCancelledAt(LocalDateTime.now());

        Reservation saveReservation = reservationRepository.save(reservation);

        if(cancelledPosition!=null)
        {
            reservationRepository.updateQueuePosition(reservation.getBook().getId() , cancelledPosition);
        }

        return reservationMapper.toDTO(saveReservation);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ReservationDTO fulfillReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new ReservationNotFoundException("Reservation not found with id "+reservationId));


        if(reservation.getBook().getAvailableCopies()<=0)
        {
            throw new BookNotAvailableException("Book is not available for Reservation");
        }

        reservation.setStatus(ReservationStatus.FULFILLED);
        reservation.setFulfilledAt(LocalDateTime.now());

        Reservation saveReservation = reservationRepository.save(reservation);

        CheckoutBookRequestDTO requestDTO = CheckoutBookRequestDTO.builder()
                .bookId(reservation.getBook().getId())
                .notes(reservation.getNotes())
                .build();

        bookLoanService.checkoutBookForUser(reservation.getUser().getId(),requestDTO);

        return reservationMapper.toDTO(saveReservation);
    }


    @Override
    public PageResponseDTO<ReservationDTO> getMyReservations(ReservationSearchRequestDTO reservationSearchRequest) {
        UserDTO user = userService.getCurrentUser();
        reservationSearchRequest.setUserId(user.getId());
        return searchReservations(reservationSearchRequest);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponseDTO<ReservationDTO> searchReservations(ReservationSearchRequestDTO reservationSearchRequest) {
         Pageable pageable = createPageable(reservationSearchRequest.getPage(),
                reservationSearchRequest.getSize(),
                reservationSearchRequest.getSortBy(),
                reservationSearchRequest.getSortDirection());

        Page<Reservation> reservationPage = reservationRepository.searchReservationWithFilters(
                reservationSearchRequest.getUserId(),
                reservationSearchRequest.getBookId(),
                reservationSearchRequest.getStatus(),
                Boolean.TRUE.equals(
                        reservationSearchRequest.getActiveOnly()
                ),
                pageable
        );

        return convertToPageResponse(reservationPage);
    }

    private Pageable createPageable(int page , int size , String sortBy, String sortDir)
    {
        page = Math.min(page,10);
        page = Math.max(page,0);

        Sort sort = sortDir.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return PageRequest.of(page,size,sort);
    }

    private PageResponseDTO<ReservationDTO> convertToPageResponse(Page<Reservation> reservationPage)
    {
        List<ReservationDTO> reservationDTOS = reservationPage.getContent()
                .stream()
                .map(reservationMapper::toDTO)
                .toList();

        return PageResponseDTO.<ReservationDTO>builder()
                .content(reservationDTOS)
                .pageNumber(reservationPage.getNumber())
                .pageSize(reservationPage.getSize())
                .totalPages(reservationPage.getTotalPages())
                .totalElements(reservationPage.getTotalElements())
                .first(reservationPage.isFirst())
                .last(reservationPage.isLast())
                .empty(reservationPage.isEmpty())
                .build();
    }
}
