package com.charu.library_management_system.models;

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
    private FineType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private FineStatus status;

    @Column(length = 500)
    private String reason;

    @Column(length = 1000)
    private String notes;

    private User waivedBy;

    private LocalDateTime waivedAt;

    private String waiverReason;

    private LocalDateTime paidAt;

    private User processedBy;

    private String transactionId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
