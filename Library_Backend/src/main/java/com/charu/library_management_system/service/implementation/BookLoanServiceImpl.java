package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.BookLoanDTO;
import com.charu.library_management_system.dto.SubscriptionDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.BookLoanSearchRequestDTO;
import com.charu.library_management_system.dto.requestDTO.CheckInRequestDTO;
import com.charu.library_management_system.dto.requestDTO.CheckoutBookRequestDTO;
import com.charu.library_management_system.dto.requestDTO.RenewalRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.enums.BookLoanType;
import com.charu.library_management_system.exception.*;
import com.charu.library_management_system.mapper.BookLoanMapper;
import com.charu.library_management_system.mapper.BookMapper;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.Book;
import com.charu.library_management_system.models.BookLoan;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.BookLoanRepository;
import com.charu.library_management_system.repository.BookRepository;
import com.charu.library_management_system.service.BookLoanService;
import com.charu.library_management_system.service.BookService;
import com.charu.library_management_system.service.SubscriptionService;
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
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SubscriptionService subscriptionService;
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookLoanMapper bookLoanMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookLoanDTO checkoutBook(CheckoutBookRequestDTO checkoutBookRequest) {
        UserDTO user = userService.getCurrentUser();

        return checkoutBookForUser(user.getId(),checkoutBookRequest);
    }

    @Override
    @Transactional
    public BookLoanDTO checkoutBookForUser(Long userId, CheckoutBookRequestDTO checkoutBookRequest) {
        // 1 ----->  Validate User exist
        UserDTO userDTO = userService.findById(userId);
        User user = userMapper.toEntity(userDTO);

        //2 -------> Validate user has active subscription
        SubscriptionDTO subscriptionDTO = subscriptionService.getUsersActiveSubscription(userId);

        // 3 ------>  Validate book exists and is available
        BookDTO bookDTO = bookService.getBookById(checkoutBookRequest.getBookId());
        Book book = bookMapper.toEntity(bookDTO);

        if(!book.isActive())
        {
            throw new BookNotActiveException("Book selected by user is not active " + book.getTitle());
        }
        if(book.getAvailableCopies()<=0)
        {
            throw new BookNotAvailableException("Book selected is not available "+book.getTitle());
        }

        //4 ------> Check if user already has this book checkout
        if(bookLoanRepository.hasActiveCheckout(userId,checkoutBookRequest.getBookId())){
            throw new BookAlreadyBorrowedException("Book already has active checkout");
        }

        //5 ------> Check users active checkout limit
        long activeCheckoutCount = bookLoanRepository.countActiveBookLoansByUser(userId);
        long maxBooksAllowed = subscriptionDTO.getMaxBooksAllowed();

        if(activeCheckoutCount>=maxBooksAllowed)
        {
            throw new BookCheckoutLimitExceededException(
                    "You have reached the maximum number of books allowed by your subscription"
            );
        }

        //6 ------> Check for overdue books
        long overdueBookCount = bookLoanRepository.countOverdueBookLoansByUser(userId);
        if(overdueBookCount>0)
        {
            throw new OverdueBookExistsException(
                    "Please return your overdue book(s) before borrowing another book"
            );
        }

        //7 -----> Create Book loan
        LocalDateTime checkoutDate = LocalDateTime.now();
        BookLoan bookLoan = BookLoan.builder()
                .user(user)
                .book(book)
                .type(BookLoanType.CHECKOUT)
                .status(BookLoanStatus.CHECKED_OUT)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(checkoutBookRequest.getCheckoutDays()))
                .renewalCount(0)
                .maxRenewals(2)
                .notes("Book Loan taken by user "+user.getFullName())
                .isOverdue(false)
                .overdueDays(0)
                .build();

        // 8 -------> Update Available Copies
        book.setAvailableCopies(book.getAvailableCopies()-1);

        // 9 -------> Save book
        bookRepository.save(book);
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);

        return bookLoanMapper.toDTO(savedBookLoan);
    }

    @Override
    @Transactional
    public BookLoanDTO checkinBook(CheckInRequestDTO checkInRequest) {
        //1. Validate if book loan exists
        BookLoan bookLoan = bookLoanRepository.findById(checkInRequest.getBookLoanId())
                .orElseThrow(()->new BookLoanNotFoundException("Book Loan not found for id "+checkInRequest.getBookLoanId()));

        //2. Check if book already returned
        if(!bookLoan.isActive())
        {
            throw new BookAlreadyReturnedException("Book is already returned by user");
        }

        //Check Ownership
        UserDTO user = userService.getCurrentUser();

        if(!user.getId().equals(bookLoan.getUser().getId()))
        {
            throw new AccessDeniedException("You do not have permission to return this book");
        }

        //3. set return date
        bookLoan.setReturnDate(LocalDateTime.now());

        //4. Get Book Loan Condition
        BookLoanStatus condition = checkInRequest.getCondition();
        if(condition==null)
        {
            condition = BookLoanStatus.RETURNED;
        }
        bookLoan.setStatus(condition);

        //5. Fine todo
        bookLoan.setOverdueDays(0);
        bookLoan.setIsOverdue(false);

        //6. Set Notes
        bookLoan.setNotes(checkInRequest.getNotes()!=null
                        ? checkInRequest.getNotes()
                        :"Book returned by user");

        //7. Update availability of book
        if(condition!=BookLoanStatus.LOST)
        {
            Book book = bookLoan.getBook();
            book.setAvailableCopies(book.getAvailableCopies()+1);
            bookRepository.save(book);
        }

        //8. Save bookLoan
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toDTO(savedBookLoan);
    }

    @Override
    @Transactional
    public BookLoanDTO renewCheckout(RenewalRequestDTO renewalRequest) {
        //1. Validate if book loan exists
        BookLoan bookLoan = bookLoanRepository.findById(renewalRequest.getBookLoanId())
                .orElseThrow(()->new BookLoanNotFoundException("Book Loan not found for id "+renewalRequest.getBookLoanId()));

        //Check Ownership
        UserDTO user = userService.getCurrentUser();

        if(!user.getId().equals(bookLoan.getUser().getId()))
        {
            throw new AccessDeniedException("You do not have permission to renew this book");
        }

        //2 . Check if book can be renewed or not
        if(!bookLoan.canRenew())
        {
            throw new BookCannotBeRenewedException("Book cannot be renewed for id "+renewalRequest.getBookLoanId());
        }

        //3. update due date
        bookLoan.setDueDate(bookLoan.getDueDate().plusDays(renewalRequest.getExtensionDays()));
        bookLoan.setRenewalCount(bookLoan.getRenewalCount()+1);
        bookLoan.setNotes(renewalRequest.getNotes()!=null
                        ? renewalRequest.getNotes()
                        : "Book renewed by user");

        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toDTO(savedBookLoan);
    }

    @Override
    public PageResponseDTO<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) {
        UserDTO user = userService.getCurrentUser();

        Page<BookLoan> bookLoanPage;

        if(status!=null)
        {
            //return only checkouts based on status , sorted by due date
            Pageable pageable = createPageable(page, size , "dueDate", "ASC");
            bookLoanPage = bookLoanRepository.findByUserIdAndStatus(user.getId(),status,pageable);
        }else{
            //return all history (both active and returned , sorted by descending
            Pageable pageable = createPageable(page,size,"createdAt","DESC");
            bookLoanPage = bookLoanRepository.findByUserId(user.getId(),pageable);
        }

        return convertToPageResponse(bookLoanPage);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponseDTO<BookLoanDTO> getBookLoans(BookLoanSearchRequestDTO bookLoanSearchRequest) {
        Pageable pageable = createPageable(bookLoanSearchRequest.getPage(),
                bookLoanSearchRequest.getSize(),
                bookLoanSearchRequest.getSortBy(),
                bookLoanSearchRequest.getSortDir());

        Page<BookLoan> bookLoanPage;

        if(Boolean.TRUE.equals(bookLoanSearchRequest.getOverdueOnly()))
        {
            bookLoanPage = bookLoanRepository.findOverdueBookLoans(LocalDateTime.now(),pageable);
        }
        else if(bookLoanSearchRequest.getUserId()!=null && bookLoanSearchRequest.getStatus()!=null)
        {
            bookLoanPage = bookLoanRepository.findByUserIdAndStatus(bookLoanSearchRequest.getUserId(),bookLoanSearchRequest.getStatus(),pageable);
        }
        else if(bookLoanSearchRequest.getUserId()!=null)
        {
            bookLoanPage = bookLoanRepository.findByUserId(bookLoanSearchRequest.getUserId(),pageable);
        }
        else if(bookLoanSearchRequest.getBookId()!=null)
        {
            bookLoanPage = bookLoanRepository.findByBookId(bookLoanSearchRequest.getBookId(), pageable);
        }
        else if(bookLoanSearchRequest.getStatus()!=null)
        {
            bookLoanPage = bookLoanRepository.findByStatus(bookLoanSearchRequest.getStatus(),pageable);
        }
        else if(bookLoanSearchRequest.getStartDate()!=null && bookLoanSearchRequest.getEndDate()!=null)
        {
            bookLoanPage = bookLoanRepository.findBookLoansByDateRange(bookLoanSearchRequest.getStartDate(),bookLoanSearchRequest.getEndDate(),pageable);
        }
        else{
            bookLoanPage = bookLoanRepository.findAll(pageable);
        }

        return convertToPageResponse(bookLoanPage);
    }

    @Override
    public int updateOverdueBookLoan() {
        return 0;
    }

    private Pageable createPageable(int page , int pageSize , String sortBy , String sortDirection)
    {
        page = Math.max(page, 0);

        pageSize = Math.min(pageSize,10);
        pageSize = Math.max(pageSize,1);

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return PageRequest.of(page,pageSize,sort);
    }

    private PageResponseDTO<BookLoanDTO> convertToPageResponse(Page<BookLoan> bookLoanPage)
    {
        List<BookLoanDTO> bookLoanDTOS = bookLoanPage.getContent()
                .stream()
                .map(bookLoanMapper::toDTO)
                .toList();

        return PageResponseDTO.<BookLoanDTO>builder()
                .content(bookLoanDTOS)
                .pageNumber(bookLoanPage.getNumber())
                .pageSize(bookLoanPage.getSize())
                .totalPages(bookLoanPage.getTotalPages())
                .totalElements(bookLoanPage.getTotalElements())
                .first(bookLoanPage.isFirst())
                .last(bookLoanPage.isLast())
                .empty(bookLoanPage.isEmpty())
                .build();
    }
}
