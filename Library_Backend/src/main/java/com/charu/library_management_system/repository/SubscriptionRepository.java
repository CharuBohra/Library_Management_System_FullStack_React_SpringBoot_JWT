package com.charu.library_management_system.repository;

import com.charu.library_management_system.dto.SubscriptionDTO;
import com.charu.library_management_system.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    @Query("Select s from Subscription s where s.user.id = :userId AND "+
    "s.active = true AND "+
    "s.startDate <= :today and s.endDate >= :today ")
    Optional<Subscription> getUsersActiveSubscription(Long userId, LocalDate today);

    @Query("Select s from Subscription s where s.active = true AND "+
    "s.endDate < :today")
    List<Subscription> findExpiredActiveSubscription(@Param("today") LocalDate today);
}
