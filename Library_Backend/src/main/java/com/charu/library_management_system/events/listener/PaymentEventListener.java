package com.charu.library_management_system.events.listener;

import com.charu.library_management_system.models.Payment;
import com.charu.library_management_system.service.FineService;
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
    private final FineService fineService;

    @Async
    @Transactional
    @EventListener
    public void handlePaymentSuccess(Payment payment)
    {
        switch (payment.getPaymentType())
        {
            case FINE:
                fineService.markFineAsPaid(payment.getFine().getId(),payment.getAmount(), payment.getTransactionId());
                break;
            case LOST_BOOK_PENALTY,
                 DAMAGED_BOOK_PENALTY:
                              break;
            case MEMBERSHIP:
                subscriptionService.activateSubscription(payment.getSubscription().getId(),payment.getId());
        }
    }
}
