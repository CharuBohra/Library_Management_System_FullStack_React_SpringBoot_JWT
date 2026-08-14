package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.SubscriptionPlanDTO;
import com.charu.library_management_system.models.SubscriptionPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubscriptionPlanMapper {

    @Mapping(target = "id", source = "id")
    SubscriptionPlanDTO toDTO(SubscriptionPlan subscriptionPlan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    SubscriptionPlan toEntity(SubscriptionPlanDTO subscriptionPlanDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromDTO(SubscriptionPlanDTO updateSubscriptionPlanDTO , @MappingTarget SubscriptionPlan subscriptionPlan);
}
