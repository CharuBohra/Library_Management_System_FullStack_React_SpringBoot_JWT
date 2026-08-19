package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.PaymentDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.PaymentInitiateRequest;
import com.charu.library_management_system.dto.requestDTO.PaymentVerifyRequest;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import com.charu.library_management_system.dto.responseDTO.PaymentLinkResponse;
import com.charu.library_management_system.enums.PaymentGateway;
import com.charu.library_management_system.enums.PaymentStatus;
import com.charu.library_management_system.exception.SubscriptionNotFoundException;
import com.charu.library_management_system.exception.UserNotFoundException;
import com.charu.library_management_system.models.Payment;
import com.charu.library_management_system.models.Subscription;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.PaymentRepository;
import com.charu.library_management_system.repository.SubscriptionRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.service.PaymentService;
import com.charu.library_management_system.service.UserService;
import com.charu.library_management_system.service.gateway.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest paymentInitiateRequest) {
        UserDTO userDTO = userService.getCurrentUser();
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(()->new UserNotFoundException("User not found with id "+userDTO.getId()));

        Subscription subscription = subscriptionRepository.findById(paymentInitiateRequest.getSubscriptionId())
                .orElseThrow(()->new SubscriptionNotFoundException("Subscription not found for id "+paymentInitiateRequest.getSubscriptionId()));

        if (!subscription.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(
                    "You cannot make payment for this subscription"
            );
        }

        Payment payment = Payment.builder()
                .user(user)
                .subscription(subscription)
                .paymentType(paymentInitiateRequest.getPaymentType())
                .paymentStatus(PaymentStatus.PENDING)
                .amount(subscription.getPrice())
                .currency(subscription.getCurrency())
                .paymentGateway(paymentInitiateRequest.getPaymentGateway())
                .transactionId("TXN_"+ UUID.randomUUID().toString())
                .description(paymentInitiateRequest.getDescription())
                .retryCount(0)
                .initiatedAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        PaymentInitiateResponse response = null;

        if(paymentInitiateRequest.getPaymentGateway()== PaymentGateway.RAZORPAY)
        {
            PaymentLinkResponse paymentLinkResponse = razorpayService.createPaymentLink(user,payment);
            response = PaymentInitiateResponse.builder()
                    .paymentId(payment.getId())
                    .transactionId(payment.getTransactionId())
                    .paymentGateway(payment.getPaymentGateway())
                    .amount(payment.getAmount())
                    .description(payment.getDescription())
                    .checkoutUrl(paymentLinkResponse.getPayment_link_url())
                    .message("Payment initiated successfully")
                    .success(true)
                    .build();
            payment.setGatewayOrderId(paymentLinkResponse.getPayment_link_id());
        }

        payment.setPaymentStatus(PaymentStatus.PROCESSING);
        paymentRepository.save(payment);
        return response;
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest paymentVerifyRequest) {
        return null;
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return null;
    }
}
