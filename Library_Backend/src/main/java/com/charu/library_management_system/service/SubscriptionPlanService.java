package com.charu.library_management_system.service;

import com.charu.library_management_system.dto.SubscriptionPlanDTO;
import com.charu.library_management_system.models.SubscriptionPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionPlanService {

    SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO);

    SubscriptionPlanDTO updateSubscriptionPlan(Long planId,SubscriptionPlanDTO updatePlanDTO);

    void deleteSubscriptionPlan(Long planId);

    List<SubscriptionPlanDTO> getAllSubscriptionPlans();

    SubscriptionPlan getSubscriptionByPlanCode(String planCode);


}
