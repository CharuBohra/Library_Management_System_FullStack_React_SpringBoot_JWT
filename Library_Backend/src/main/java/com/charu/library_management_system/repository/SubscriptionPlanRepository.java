package com.charu.library_management_system.repository;

import com.charu.library_management_system.models.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan,Long> {
    Boolean existsByPlanCode(String planCode);
}
