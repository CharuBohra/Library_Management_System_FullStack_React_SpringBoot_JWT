package com.charu.library_management_system.mapper;

import com.charu.library_management_system.dto.PaymentDTO;
import com.charu.library_management_system.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName" , source = "user.fullName")
    @Mapping(target = "userEmail" , source ="user.email" )
    @Mapping(target = "subscriptionId" , source = "subscription.id")
    @Mapping(target = "planName" , source = "subscription.planName")
    @Mapping(target = "planCode", source = "subscription.planCode")
    PaymentDTO toDTO(Payment payment);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "subscription", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "gatewayPaymentId", ignore = true)
    @Mapping(target = "gatewayOrderId", ignore = true)
    @Mapping(target = "gatewayPaymentSignature", ignore = true)
    @Mapping(target = "initiatedAt", ignore = true)
    @Mapping(target = "completedAt" , ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt" , ignore = true)
    Payment toEntity(PaymentDTO paymentDTO);
}
