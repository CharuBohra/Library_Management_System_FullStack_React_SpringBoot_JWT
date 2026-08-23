package com.charu.library_management_system.events.publisher;

import com.charu.library_management_system.models.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishPaymentSuccessEvent(Payment payment)
    {
        applicationEventPublisher.publishEvent(payment);
    }
}
