package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.SubscriptionDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public interface SubscriptionService {

    PaymentInitiateResponse subscribe(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO getUsersActiveSubscription();

    SubscriptionDTO cancelSubscription(Long id , String reason) throws AccessDeniedException;

    SubscriptionDTO activateSubscription(Long id , Long paymentId);

    PageResponseDTO<SubscriptionDTO> getAllSubscriptions(Pageable pageable);

    void deactivateExpiredSubscriptions();
}
