package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookDTO;
import com.charu.library_management_system.dto.BookLoanDTO;
import com.charu.library_management_system.dto.SubscriptionDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.CheckInRequestDTO;
import com.charu.library_management_system.dto.requestDTO.CheckoutBookRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.mapper.BookMapper;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.Book;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.BookLoanRepository;
import com.charu.library_management_system.service.BookLoanService;
import com.charu.library_management_system.service.BookService;
import com.charu.library_management_system.service.SubscriptionService;
import com.charu.library_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SubscriptionService subscriptionService;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Override
    public BookLoanDTO checkoutBook(CheckoutBookRequestDTO checkoutBookRequest) {
        UserDTO user = userService.getCurrentUser();

        return checkoutBookForUser(user.getId(),checkoutBookRequest);
    }

    @Override
    public BookLoanDTO checkoutBookForUser(Long userId, CheckoutBookRequestDTO checkoutBookRequest) {
        // 1 ----->  Validate User exist
        UserDTO userDTO = userService.findById(userId);
        User user = userMapper.toEntity(userDTO);

        //2 -------> Validate user has active subscription
        SubscriptionDTO subscriptionDTO = subscriptionService.getUsersActiveSubscription();

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

        //4 . Check if user already has this book checkout
        if(bookLoanRepository.hasActiveCheckout(userId,checkoutBookRequest.getBookId()){
            throw new BookAlreadyBorrowedException("Book already has active checkout");
        }

        //5. Check users active checkout limit
        long activeCheckout = bookLoanRepository.countActiveBookLoanByUser(userId);
        long maxBooksAllowed = subscriptionDTO.getMaxBooksAllowed();

        if(activeCheckout>=maxBooksAllowed)
        {
            throw new BookCheckoutLimitExceededException("Reached maximum number of books allowed")
        }

        //6 . Check for overdue books
        Long overdueCount = bookLoanRepository.countOverdueBookLoanByUser(userId);

        return null;
    }

    @Override
    public BookLoanDTO checkinBook(CheckInRequestDTO checkInRequest) {
        return null;
    }

    @Override
    public BookLoanDTO renewCheckout(RenewalRequestDTO renewalRequest) {
        return null;
    }

    @Override
    public PageResponseDTO<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) {
        return null;
    }

    @Override
    public PageResponseDTO<BookLoanDTO> getBookLoans(BookLoanSearchRequestDTO bookLoanSearchRequest) {
        return null;
    }

    @Override
    public int updateOverdueBookLoan() {
        return 0;
    }
}
