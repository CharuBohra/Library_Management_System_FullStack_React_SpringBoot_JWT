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

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "plan", source = "plan")
    Subscription toEntity(SubscriptionDTO subscriptionDTO, User user , SubscriptionPlan subscriptionPlan);
}
