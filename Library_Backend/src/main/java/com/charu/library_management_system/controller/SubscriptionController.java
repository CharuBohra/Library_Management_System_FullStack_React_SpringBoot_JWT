package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.SubscriptionDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import com.charu.library_management_system.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<PaymentInitiateResponse> subscribeToPlan(@Valid @RequestBody SubscriptionDTO subscriptionDTO)
    {
        PaymentInitiateResponse subscription = subscriptionService.subscribe(subscriptionDTO);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponseDTO<SubscriptionDTO>> getAllSubscriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ){
        size = Math.min(size,5);
        size = Math.max(size,1);

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        PageResponseDTO<SubscriptionDTO> subscriptions = subscriptionService.getAllSubscriptions(pageable);
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/deactivate-expired")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deactivateExpiredSubscriptions()
    {
        subscriptionService.deactivateExpiredSubscriptions();
        ApiResponse apiResponse = new ApiResponse("Deactivated expired subscriptions",true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/active")
    public ResponseEntity<SubscriptionDTO> getUserActiveSubscription()
    {
        SubscriptionDTO subscriptionDTO = subscriptionService.getUsersActiveSubscription();
        return ResponseEntity.ok(subscriptionDTO);
    }

    @PostMapping("/{subscriptionId}/cancel")
    public ResponseEntity<SubscriptionDTO> cancelSubscription(@PathVariable("subscriptionId") Long id ,
                                                              @RequestParam(required = false) String reason){
        SubscriptionDTO subscription = subscriptionService.cancelSubscription(id,reason);
        return ResponseEntity.ok(subscription);
    }
}
