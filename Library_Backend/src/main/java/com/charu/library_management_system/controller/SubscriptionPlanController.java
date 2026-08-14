package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.SubscriptionPlanDTO;
import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import com.charu.library_management_system.service.SubscriptionPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    @PostMapping
    public ResponseEntity<SubscriptionPlanDTO> createSubscriptionPlan(@Valid @RequestBody SubscriptionPlanDTO subscriptionPlanDTO)
    {
        SubscriptionPlanDTO planDTO = subscriptionPlanService.createSubscriptionPlan(subscriptionPlanDTO);
        return ResponseEntity.ok(planDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDTO> updateSubscriptionPlan(@PathVariable("id") Long id, @Valid @RequestBody SubscriptionPlanDTO updateSubscriptionPlan)
    {
        SubscriptionPlanDTO planDTO  = subscriptionPlanService.updateSubscriptionPlan(id,updateSubscriptionPlan);
        return ResponseEntity.ok(planDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSubscriptionPlan(@PathVariable("id") Long id)
    {
        subscriptionPlanService.deleteSubscriptionPlan(id);
        ApiResponse apiResponse = new ApiResponse("Deleted Subscription Plan Successfully",true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDTO>> getAllSubscriptionPlans()
    {
        List<SubscriptionPlanDTO> planDTOList = subscriptionPlanService.getAllSubscriptionPlans();
        return ResponseEntity.ok(planDTOList);
    }
}
