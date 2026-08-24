package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.SubscriptionDTO;
import com.charu.library_management_system.models.Subscription;
import com.charu.library_management_system.models.SubscriptionPlan;
import com.charu.library_management_system.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId" , source = "user.id")
    @Mapping(target = "userName" , source = "user.fullName")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "planId" , source = "plan.id")
    SubscriptionDTO toDTO(Subscription subscription);

    @Mapping(target = "id", source = "subscriptionDTO.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "plan", source = "plan")
    @Mapping(target = "planName", ignore = true)
    @Mapping(target = "planCode", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "maxBooksAllowed", ignore = true)
    @Mapping(target = "maxDaysPerBook", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "currency" , ignore = true)
    @Mapping(target = "active",ignore = true)
    Subscription toEntity(SubscriptionDTO subscriptionDTO, User user , SubscriptionPlan plan);
}
