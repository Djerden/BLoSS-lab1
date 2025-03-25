package com.djeno.lab1.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "apps")
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, unique = true)
    private String iconId; // ID иконки в MinIO

    @ElementCollection
    @CollectionTable(name = "app_screenshots", joinColumns = @JoinColumn(name = "app_id"))
    @Column(name = "screenshot")
    private List<String> screenshotsIds; // Список ID скриншотов в MinIO

    @Column(nullable = false, unique = true)
    private String fileId; // ID файла в MinIO

    @Column(nullable = false)
    private BigDecimal price; // 0 - если бесплатное

    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "app_category",
            joinColumns = @JoinColumn(name = "app_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
