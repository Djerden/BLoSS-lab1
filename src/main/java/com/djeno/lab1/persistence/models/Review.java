package com.djeno.lab1.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "app_id", nullable = false)
    private App app;

    @Column(nullable = false)
    private int rating; // Оценка 1-5

    @Column(length = 1000)
    private String comment;
}
