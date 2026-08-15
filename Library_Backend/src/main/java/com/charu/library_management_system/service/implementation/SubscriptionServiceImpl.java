package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.SubscriptionDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.exception.ActiveSubscriptionNotFoundException;
import com.charu.library_management_system.exception.SubscriptionAlreadyInactiveException;
import com.charu.library_management_system.exception.SubscriptionNotFoundException;
import com.charu.library_management_system.exception.SubscriptionPlanNotFoundException;
import com.charu.library_management_system.mapper.SubscriptionMapper;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.Subscription;
import com.charu.library_management_system.models.SubscriptionPlan;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.SubscriptionPlanRepository;
import com.charu.library_management_system.repository.SubscriptionRepository;
import com.charu.library_management_system.service.SubscriptionPlanService;
import com.charu.library_management_system.service.SubscriptionService;
import com.charu.library_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) {
        UserDTO userDTO = userService.getCurrentUser();
        User user = userMapper.toEntity(userDTO);

        SubscriptionPlan plan = subscriptionPlanRepository.findById(subscriptionDTO.getPlanId())
                .orElseThrow(()->new SubscriptionPlanNotFoundException("Subscription plan not found for id "+subscriptionDTO.getPlanId()));

        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);

        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setActive(false);

        subscription.initializeFromPlan();

        subscriptionRepository.save(subscription);

        //create payment

        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO getUsersActiveSubscription() {
        UserDTO userDTO = userService.getCurrentUser();

        Subscription subscription = subscriptionRepository.getUsersActiveSubscription(userDTO.getId() , LocalDate.now())
                .orElseThrow(()-> new ActiveSubscriptionNotFoundException("No active subscriptions found for user id "+userDTO.getId()));

        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    @Transactional
    public SubscriptionDTO cancelSubscription(Long id, String reason) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(()-> new SubscriptionNotFoundException("Subscription not found for id "+id));

        UserDTO user = userService.getCurrentUser();
        if (!user.getId().equals(subscription.getUser().getId())) {
            throw new AccessDeniedException(
                    "You do not have permission to cancel this subscription"
            );
        }

        if(!subscription.getActive())
        {
            throw new SubscriptionAlreadyInactiveException("Subscription already inactive for id "+id);
        }

        subscription.setActive(false);
        subscription.setCancelledAt(LocalDateTime.now());
        subscription.setCancellationReason(reason != null ? reason : "Cancelled by User");

        subscriptionRepository.save(subscription);

        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    @Transactional
    public SubscriptionDTO activateSubscription(Long id, Long paymentId) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(()-> new SubscriptionNotFoundException("Subscription not found for id "+id));

        //verify payment

        subscription.setActive(true);

        subscriptionRepository.save(subscription);

        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public PageResponseDTO<SubscriptionDTO> getAllSubscriptions(Pageable pageable) {
        Page<Subscription> subscriptionsPage = subscriptionRepository.findAll(pageable);

        List<SubscriptionDTO> subscriptions = subscriptionsPage.getContent()
                .stream()
                .map(subscriptionMapper::toDTO)
                .toList();

        PageResponseDTO<SubscriptionDTO> pageResponseDTO = PageResponseDTO.<SubscriptionDTO>builder()
                .content(subscriptions)
                .pageNumber(subscriptionsPage.getNumber())
                .pageSize(subscriptionsPage.getSize())
                .totalPages(subscriptionsPage.getTotalPages())
                .totalElements(subscriptionsPage.getTotalElements())
                .first(subscriptionsPage.isFirst())
                .last(subscriptionsPage.isLast())
                .empty(subscriptionsPage.isEmpty())
                .build();

        return pageResponseDTO;
    }

    @Override
    @Transactional
    public void deactivateExpiredSubscriptions() {
        List<Subscription> expiredSubscriptions = subscriptionRepository.findExpiredActiveSubscription(LocalDate.now());

        for(Subscription subscription : expiredSubscriptions)
        {
            subscription.setActive(false);
            subscriptionRepository.save(subscription);
        }
    }
}
