package com.charu.library_management_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "plan_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SubscriptionPlan plan;

    @Column(nullable = false)
    private String planName;

    @Column(nullable = false)
    private String planCode;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Builder.Default
    @Column(nullable = false , length = 3)
    private String currency="INR";

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Builder.Default
    private Boolean active = false;

    @Column(nullable = false)
    private Integer maxBooksAllowed;

    @Column(nullable = false)
    private Integer maxDaysPerBook;

    @Builder.Default
    private  boolean autoRenew = false;

    private LocalDateTime cancelledAt;

    private String cancellationReason;

    private String notes;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public boolean isValid()
    {
        if(!active){
            return false;
        }

        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }

    public boolean isExpired()
    {
        return LocalDate.now().isAfter(endDate);
    }

    public long getDaysRemaining()
    {
        if(endDate == null || isExpired())
        {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(),endDate);
    }

    public void calculateEndDate(){
        if(plan!=null && startDate!=null)
        {
            this.endDate = startDate.plusDays(plan.getDurationDays());
        }
    }

    public void initializeFromPlan()
    {
        if(plan!=null)
        {
            this.planName = plan.getName();
            this.planCode = plan.getPlanCode();
            this.price = plan.getPrice();
            this.currency = plan.getCurrency();
            this.maxBooksAllowed = plan.getMaxBooksAllowed();
            this.maxDaysPerBook=plan.getMaxDaysPerBook();
            if(startDate==null)
            {
                this.startDate = LocalDate.now();
            }
            calculateEndDate();
        }
    }

    public BigDecimal getPriceInMajorUnits()
    {
        if(price==null)
        {
            return BigDecimal.ZERO;
        }
        return price;
    }

    public boolean canBorrowMoreBooks(int currentBorrowedBooks)
    {
        return isValid() && currentBorrowedBooks<maxBooksAllowed;
    }
}
