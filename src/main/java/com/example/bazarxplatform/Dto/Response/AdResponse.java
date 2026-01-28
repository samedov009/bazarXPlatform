package com.example.bazarxplatform.Dto.Response;

import com.example.bazarxplatform.Enums.AdStatus;
import com.example.bazarxplatform.Enums.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdResponse {

    private Long id;
    private String adType; // "CAR", "PHONE", "HOME", "JOB"

    // User info
    private Long userId;
    private String username;

    // Status
    private AdStatus status;

    // Contact
    private String contactName;
    private String contactPhone;
    private String contactEmail;

    // Common fields
    private BigDecimal price;
    private City city;
    private String description;

    // Featured
    private Boolean isSearchFeatured;
    private Boolean isCategoryFeatured;
    private Boolean isAppFeatured;
    private LocalDateTime searchFeaturedUntil;
    private LocalDateTime categoryFeaturedUntil;
    private LocalDateTime appFeaturedUntil;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private LocalDateTime expiresAt;

    // Stats
    private Integer viewCount;

    // Images
    private List<String> imageUrls;
    private String primaryImageUrl;

    // Specific fields (populated based on ad type)
    private Object specificData; // CarAdData, PhoneAdData, HomeAdData, or JobAdData
}
