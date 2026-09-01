package com.charu.library_management_system.models;

import com.charu.library_management_system.enums.BookLoanStatus;
import com.charu.library_management_system.enums.BookLoanType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_loan")
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookLoanType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookLoanStatus status;

    @Column(nullable = false)
    private LocalDateTime checkoutDate;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    private LocalDateTime returnDate;

    @Column(nullable = false)
    @Builder.Default
    private Integer renewalCount=0;

    @Column(nullable = false)
    @Builder.Default
    private Integer maxRenewals = 2;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isOverdue = false;

    @Column(nullable = false)
    @Builder.Default
    private Integer overdueDays = 0;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isActive()
    {
        return status==BookLoanStatus.CHECKED_OUT ||  status==BookLoanStatus.OVERDUE;
    }

    public boolean canRenew()
    {
        return status==BookLoanStatus.CHECKED_OUT && !isOverdue && renewalCount<maxRenewals;
    }
}
