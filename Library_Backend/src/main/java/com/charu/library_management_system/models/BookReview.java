package com.charu.library_management_system.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="book_review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id" ,nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id" , nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false , length = 2000)
    private String reviewText;

    @Column(length = 200)
    private String title;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
