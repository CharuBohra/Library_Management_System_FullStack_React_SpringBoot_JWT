package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.SubscriptionPlanDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.exception.SubscriptionPlanAlreadyExistsException;
import com.charu.library_management_system.exception.SubscriptionPlanNotFoundException;
import com.charu.library_management_system.mapper.SubscriptionPlanMapper;
import com.charu.library_management_system.models.SubscriptionPlan;
import com.charu.library_management_system.repository.SubscriptionPlanRepository;
import com.charu.library_management_system.service.SubscriptionPlanService;
import com.charu.library_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionPlanMapper subscriptionPlanMapper;
    private final UserService userService;

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) {
        if(subscriptionPlanRepository.existsByPlanCode(planDTO.getPlanCode())){
            throw new SubscriptionPlanAlreadyExistsException("Subscription plan already exists for planCode "+planDTO.getPlanCode());
        }
        SubscriptionPlan subscriptionPlan = subscriptionPlanMapper.toEntity(planDTO);

        UserDTO userDTO = userService.getCurrentUser();

        subscriptionPlan.setCreatedBy(userDTO.getEmail());
        subscriptionPlan.setUpdatedBy(userDTO.getEmail());

        subscriptionPlanRepository.save(subscriptionPlan);

        return subscriptionPlanMapper.toDTO(subscriptionPlan);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO updatePlanDTO) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(()-> new SubscriptionPlanNotFoundException("Subscription plan not found for id "+ planId));

        subscriptionPlanMapper.updateEntityFromDTO(updatePlanDTO , subscriptionPlan);

        UserDTO userDTO = userService.getCurrentUser();

        subscriptionPlan.setUpdatedBy(userDTO.getEmail());

        subscriptionPlanRepository.save(subscriptionPlan);

        return subscriptionPlanMapper.toDTO(subscriptionPlan);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSubscriptionPlan(Long planId) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(()-> new SubscriptionPlanNotFoundException("Subscription plan not found for id "+ planId));

        subscriptionPlanRepository.delete(subscriptionPlan);
    }

    @Override
    public List<SubscriptionPlanDTO> getAllSubscriptionPlans() {
        List<SubscriptionPlanDTO> subscriptionPlanDTOList = subscriptionPlanRepository.findAll()
                .stream()
                .map(subscriptionPlanMapper::toDTO)
                .toList();

        return subscriptionPlanDTOList;
    }
}
