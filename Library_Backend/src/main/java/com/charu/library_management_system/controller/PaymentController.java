package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.PaymentDTO;
import com.charu.library_management_system.dto.requestDTO.PaymentVerifyRequest;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayments(@Valid @RequestBody PaymentVerifyRequest request)
    {
        PaymentDTO paymentDTO = paymentService.verifyPayment(request);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponseDTO<PaymentDTO>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    )
    {
        Sort sort = sortDirection.equalsIgnoreCase("DESC")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,pageSize,sort);

        PageResponseDTO<PaymentDTO> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/payment_success/{paymentId}")
    public ResponseEntity<Void> paymentSuccess(@PathVariable Long paymentId , @RequestParam(value = "razorpay_payment_id",required = false) String razorpayPaymentId)
    {
        if (razorpayPaymentId == null || razorpayPaymentId.isBlank()) {
            log.warn("Payment callback for paymentId {} arrived without razorpay_payment_id", paymentId);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontendUrl + "/payment/result?status=FAILURE&paymentId=" + paymentId))
                    .build();
        }
        String target;
        try {
            PaymentDTO payment = paymentService.verifyPayment(
                    PaymentVerifyRequest.builder().razorPaymentId(razorpayPaymentId).build());
            target = frontendUrl + "/payment/result?status=" + payment.getPaymentStatus()
                    + "&paymentId=" + payment.getId();
        } catch (Exception e) {
            log.warn("Payment callback failed for paymentId {}: {}", paymentId, e.getMessage());
            target = frontendUrl + "/payment/result?status=FAILURE&paymentId=" + paymentId;
        }

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(target)).build();
    }
}
