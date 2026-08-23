package com.charu.library_management_system.events.listener;

import com.charu.library_management_system.models.Payment;
import com.charu.library_management_system.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private final SubscriptionService subscriptionService;

    @Async
    @Transactional
    @EventListener
    public void handlePaymentSuccess(Payment payment)
    {
        switch (payment.getPaymentType())
        {
            case FINE,
                 LOST_BOOK_PENALTY,
                 DAMAGED_BOOK_PENALTY:
                              break;
            case MEMBERSHIP:
                subscriptionService.activateSubscription(payment.getSubscription().getId(),payment.getId());
        }
    }
}
