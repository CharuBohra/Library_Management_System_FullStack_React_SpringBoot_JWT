package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.BookLoanDTO;
import com.charu.library_management_system.dto.requestDTO.BookLoanSearchRequestDTO;
import com.charu.library_management_system.dto.requestDTO.CheckInRequestDTO;
import com.charu.library_management_system.dto.requestDTO.CheckoutBookRequestDTO;
import com.charu.library_management_system.dto.requestDTO.RenewalRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.service.BookLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-loans")
public class BookLoanController {

    private final BookLoanService bookLoanService;

    @PostMapping("/checkout")
    public ResponseEntity<BookLoanDTO> checkoutBook(@Valid @RequestBody CheckoutBookRequestDTO checkoutBookRequestDTO)
    {
        BookLoanDTO bookLoanDTO = bookLoanService.checkoutBook(checkoutBookRequestDTO);
        return ResponseEntity.ok(bookLoanDTO);
    }

    @PostMapping("/checkout/user/{userId}")
    public ResponseEntity<BookLoanDTO> checkoutBookForUser(@PathVariable("userId") Long userId,
                                                           @Valid @RequestBody CheckoutBookRequestDTO checkoutBookRequestDTO)
    {
        BookLoanDTO bookLoanDTO = bookLoanService.checkoutBookForUser(userId,checkoutBookRequestDTO);
        return ResponseEntity.ok(bookLoanDTO);
    }

    @PostMapping("/checkin")
    public ResponseEntity<BookLoanDTO> checkin(@Valid @RequestBody CheckInRequestDTO checkInRequestDTO)
    {
        BookLoanDTO bookLoanDTO = bookLoanService.checkinBook(checkInRequestDTO);
        return ResponseEntity.ok(bookLoanDTO);
    }

    @PostMapping("/renew")
    public ResponseEntity<BookLoanDTO> renewBook(@Valid @RequestBody RenewalRequestDTO requestDTO)
    {
        BookLoanDTO bookLoanDTO = bookLoanService.renewCheckout(requestDTO);
        return ResponseEntity.ok(bookLoanDTO);
    }

    @GetMapping("/my")
    public ResponseEntity<PageResponseDTO<BookLoanDTO>> getMyBookLoans(@RequestParam(required = false)BookLoanStatus status,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "20") int size)
    {
        PageResponseDTO<BookLoanDTO> myBookLoans = bookLoanService.getMyBookLoans(status,page,size);
        return ResponseEntity.ok(myBookLoans);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponseDTO<BookLoanDTO>> getAllBookLoans(@Valid @RequestBody BookLoanSearchRequestDTO bookLoanSearchRequestDTO)
    {
        PageResponseDTO<BookLoanDTO> bookLoans = bookLoanService.getBookLoans(bookLoanSearchRequestDTO);
        return ResponseEntity.ok(bookLoans);
    }

    @PostMapping("/update-overdue")
    public ResponseEntity<Integer> updateOverdueBookLoans()
    {
        int updateOverdue = bookLoanService.updateOverdueBookLoan();
        return ResponseEntity.ok(updateOverdue);
    }
}
