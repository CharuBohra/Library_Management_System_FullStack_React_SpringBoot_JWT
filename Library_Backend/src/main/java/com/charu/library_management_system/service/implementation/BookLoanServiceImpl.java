package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.BookLoanDTO;
import com.charu.library_management_system.dto.requestDTO.CheckInRequestDTO;
import com.charu.library_management_system.dto.requestDTO.CheckoutBookRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.service.BookLoanService;

public class BookLoanServiceImpl implements BookLoanService {

    @Override
    public BookLoanDTO checkoutBook(CheckoutBookRequestDTO checkoutBookRequest) {
        return null;
    }

    @Override
    public BookLoanDTO checkoutBookForUser(Long userId, CheckoutBookRequestDTO checkoutBookRequest) {
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
