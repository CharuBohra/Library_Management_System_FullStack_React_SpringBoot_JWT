package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.BookLoanDTO;
import com.charu.library_management_system.dto.requestDTO.CheckInRequestDTO;
import com.charu.library_management_system.dto.requestDTO.CheckoutBookRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import org.springframework.stereotype.Service;

@Service
public interface BookLoanService {
    BookLoanDTO checkoutBook (CheckoutBookRequestDTO checkoutBookRequest);

    BookLoanDTO checkoutBookForUser(Long userId , CheckoutBookRequestDTO checkoutBookRequest);

    BookLoanDTO checkinBook(CheckInRequestDTO checkInRequest);

    BookLoanDTO renewCheckout(RenewalRequestDTO renewalRequest);

    PageResponseDTO<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size);

    PageResponseDTO<BookLoanDTO> getBookLoans(BookLoanSearchRequestDTO bookLoanSearchRequest);

    int updateOverdueBookLoan();
}
