package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.PaymentDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.PaymentInitiateRequest;
import com.charu.library_management_system.dto.requestDTO.PaymentVerifyRequest;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import com.charu.library_management_system.dto.responseDTO.PaymentLinkResponse;
import com.charu.library_management_system.enums.PaymentGateway;
import com.charu.library_management_system.enums.PaymentStatus;
import com.charu.library_management_system.events.publisher.PaymentEventPublisher;
import com.charu.library_management_system.exception.PaymentNotFoundException;
import com.charu.library_management_system.exception.SubscriptionNotFoundException;
import com.charu.library_management_system.exception.UserNotFoundException;
import com.charu.library_management_system.mapper.PaymentMapper;
import com.charu.library_management_system.models.Payment;
import com.charu.library_management_system.models.Subscription;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.PaymentRepository;
import com.charu.library_management_system.repository.SubscriptionRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.service.PaymentService;
import com.charu.library_management_system.service.UserService;
import com.charu.library_management_system.service.gateway.RazorpayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final PaymentMapper paymentMapper;
    private final PaymentEventPublisher paymentEventPublisher;

    @Transactional
    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest paymentInitiateRequest) {
        UserDTO userDTO = userService.getCurrentUser();
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(()->new UserNotFoundException("User not found with id "+userDTO.getId()));


        Payment payment = Payment.builder()
                .user(user)
                .paymentType(paymentInitiateRequest.getPaymentType())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentGateway(paymentInitiateRequest.getPaymentGateway())
                .transactionId("TXN_"+ UUID.randomUUID().toString())
                .description(paymentInitiateRequest.getDescription())
                .retryCount(0)
                .initiatedAt(LocalDateTime.now())
                .build();

        if(paymentInitiateRequest.getSubscriptionId()!=null)
        {
            Subscription subscription = subscriptionRepository.findById(paymentInitiateRequest.getSubscriptionId())
                    .orElseThrow(()->new SubscriptionNotFoundException("Subscription not found for id "+paymentInitiateRequest.getSubscriptionId()));

            if (!subscription.getUser().getId().equals(user.getId())) {
                throw new AccessDeniedException(
                        "You cannot make payment for this subscription"
                );
            }

            payment.setSubscription(subscription);
            payment.setAmount(subscription.getPrice());
            payment.setCurrency(subscription.getCurrency());
        }

        paymentRepository.save(payment);

        PaymentInitiateResponse response = new PaymentInitiateResponse();

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

            payment.setPaymentStatus(PaymentStatus.PROCESSING);
        }

        paymentRepository.save(payment);
        return response;
    }

    @Override
    @Transactional
    public PaymentDTO verifyPayment(PaymentVerifyRequest paymentVerifyRequest) {

        JSONObject paymentDetails = razorpayService.fetchPaymentDetails(paymentVerifyRequest.getRazorPaymentId());

        JSONObject notes = paymentDetails.getJSONObject("notes");
        Long paymentId = notes.optLong("payment_id");

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()->new PaymentNotFoundException("Payment not found for Id "+paymentId));

        boolean isValid = razorpayService.isValidPayment(paymentVerifyRequest.getRazorPaymentId());

        if(PaymentGateway.RAZORPAY == payment.getPaymentGateway())
        {
            if(isValid)
            {
                payment.setGatewayPaymentId(paymentVerifyRequest.getRazorPaymentId());
            }
        }
        if(isValid)
        {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setCompletedAt(LocalDateTime.now());
            payment = paymentRepository.save(payment);

            paymentEventPublisher.publishPaymentSuccessEvent(payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILURE);
            payment.setFailureReason("Payment verification failed");
            payment = paymentRepository.save(payment);
        }

        return paymentMapper.toDTO(payment);
    }

    @Override
    public PageResponseDTO<PaymentDTO> getAllPayments(Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findAll(pageable);

        List<PaymentDTO> payments = paymentsPage.getContent()
                .stream()
                .map(paymentMapper::toDTO)
                .toList();

        return PageResponseDTO.<PaymentDTO>builder()
                .content(payments)
                .pageNumber(paymentsPage.getNumber())
                .pageSize(paymentsPage.getSize())
                .totalPages(paymentsPage.getTotalPages())
                .totalElements(paymentsPage.getTotalElements())
                .first(paymentsPage.isFirst())
                .last(paymentsPage.isLast())
                .empty(paymentsPage.isEmpty())
                .build();
    }
}
