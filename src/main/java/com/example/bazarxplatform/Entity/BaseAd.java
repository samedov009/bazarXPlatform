package com.example.bazarxplatform.Entity;

import com.example.bazarxplatform.Enums.AdStatus;
import com.example.bazarxplatform.Enums.City;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ads")
@Inheritance(strategy = InheritanceType.JOINED)  // ⬅️ Əlavə etdik
@Data
public abstract class BaseAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdStatus status = AdStatus.PENDING;

    // Contact Information
    private String contactName;
    private String contactPhone;
    private String contactEmail;

    // Ad Details
    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private City city;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Featured Promotions
    private LocalDateTime searchFeaturedUntil;
    private LocalDateTime categoryFeaturedUntil;
    private LocalDateTime appFeaturedUntil;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private LocalDateTime expiresAt;

    // Statistics
    private Integer viewCount = 0;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}