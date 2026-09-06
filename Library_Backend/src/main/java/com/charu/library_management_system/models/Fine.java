package com.charu.library_management_system.models;

import com.charu.library_management_system.enums.FineStatus;
import com.charu.library_management_system.enums.FineType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_loan_id",nullable = false)
    private BookLoan bookLoan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FineType type;

    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FineStatus status;

    @Column(length = 500)
    private String reason;

    @Column(length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "waived_user_id")
    private User waivedBy;

    @Column(name = "waived_at")
    private LocalDateTime waivedAt;

    @Column(name = "waiver_reason",length = 500)
    private String waiverReason;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by_user_id")
    private User processedBy;

    @Column(name = "transaction_id")
    private String transactionId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void waive(User admin,String reason)
    {
        this.status = FineStatus.WAIVED;
        this.waivedBy = admin;
        this.waivedAt = LocalDateTime.now();
        this.waiverReason = reason;
    }

    public void applyPayment(BigDecimal amount)
    {
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0)
        {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount.compareTo(this.amount) != 0) {
            throw new IllegalArgumentException(
                    "Payment amount does not match fine amount"
            );
        }

        this.status = FineStatus.PAID;
        this.paidAt = LocalDateTime.now();
    }
}
