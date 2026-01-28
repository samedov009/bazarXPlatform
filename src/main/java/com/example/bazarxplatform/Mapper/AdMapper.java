package com.example.bazarxplatform.Mapper;

import com.example.bazarxplatform.Dto.Request.*;
import com.example.bazarxplatform.Dto.Response.*;
import com.example.bazarxplatform.Entity.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapper {

    // ==================== CAR AD ====================

    // Request -> Entity
    public CarAd toCarAdEntity(CreateCarAdRequest request, User user) {
        CarAd ad = new CarAd();

        // Set user
        ad.setUser(user);

        // Set common fields
        setCommonFields(ad, request.getPrice(), request.getCity(),
                request.getDescription(), request.getContactName(),
                request.getContactPhone(), request.getContactEmail(), user);

        // Set car specific fields
        ad.setBrand(request.getBrand());
        ad.setColor(request.getColor());
        ad.setEngineVolume(request.getEngineVolume());
        ad.setFuelType(request.getFuelType());
        ad.setTransmission(request.getTransmission());
        ad.setBodyType(request.getBodyType());
        ad.setYear(request.getYear());
        ad.setMileage(request.getMileage());
        ad.setIsNew(request.getIsNew());

        return ad;
    }

    // Entity -> Response
    public CarAdResponse toCarAdResponse(CarAd ad, List<String> imageUrls) {
        CarAdResponse response = new CarAdResponse();

        // Set common fields
        setCommonResponseFields(response, ad, "CAR", imageUrls);

        // Set car specific fields
        response.setBrand(ad.getBrand());
        response.setColor(ad.getColor());
        response.setEngineVolume(ad.getEngineVolume());
        response.setFuelType(ad.getFuelType());
        response.setTransmission(ad.getTransmission());
        response.setBodyType(ad.getBodyType());
        response.setYear(ad.getYear());
        response.setMileage(ad.getMileage());
        response.setIsNew(ad.getIsNew());

        return response;
    }

    // ==================== PHONE AD ====================

    public PhoneAd toPhoneAdEntity(CreatePhoneAdRequest request, User user) {
        PhoneAd ad = new PhoneAd();

        ad.setUser(user);
        setCommonFields(ad, request.getPrice(), request.getCity(),
                request.getDescription(), request.getContactName(),
                request.getContactPhone(), request.getContactEmail(), user);

        ad.setBrand(request.getBrand());
        ad.setIsNew(request.getIsNew());
        ad.setHasDelivery(request.getHasDelivery());

        return ad;
    }

    public PhoneAdResponse toPhoneAdResponse(PhoneAd ad, List<String> imageUrls) {
        PhoneAdResponse response = new PhoneAdResponse();

        setCommonResponseFields(response, ad, "PHONE", imageUrls);

        response.setBrand(ad.getBrand());
        response.setIsNew(ad.getIsNew());
        response.setHasDelivery(ad.getHasDelivery());

        return response;
    }

    // ==================== HOME AD ====================

    public HomeAd toHomeAdEntity(CreateHomeAdRequest request, User user) {
        HomeAd ad = new HomeAd();

        ad.setUser(user);
        setCommonFields(ad, request.getPrice(), request.getCity(),
                request.getDescription(), request.getContactName(),
                request.getContactPhone(), request.getContactEmail(), user);

        ad.setPropertyType(request.getPropertyType());
        ad.setAdType(request.getAdType());
        ad.setArea(request.getArea());
        ad.setRoomCount(request.getRoomCount());
        ad.setLocation(request.getLocation());

        return ad;
    }

    public HomeAdResponse toHomeAdResponse(HomeAd ad, List<String> imageUrls) {
        HomeAdResponse response = new HomeAdResponse();

        setCommonResponseFields(response, ad, "HOME", imageUrls);

        response.setPropertyType(ad.getPropertyType());
        response.setPropertyAdType(ad.getAdType());
        response.setArea(ad.getArea());
        response.setRoomCount(ad.getRoomCount());
        response.setLocation(ad.getLocation());

        return response;
    }

    // ==================== JOB AD ====================

    public JobAd toJobAdEntity(CreateJobAdRequest request, User user) {
        JobAd ad = new JobAd();

        ad.setUser(user);
        setCommonFields(ad, request.getPrice(), request.getCity(),
                request.getDescription(), request.getContactName(),
                request.getContactPhone(), request.getContactEmail(), user);

        ad.setCategory(request.getCategory());
        ad.setWorkSchedule(request.getWorkSchedule());
        ad.setExperienceRequired(request.getExperienceRequired());
        ad.setEducationLevel(request.getEducationLevel());
        ad.setSalary(request.getSalary());

        return ad;
    }

    public JobAdResponse toJobAdResponse(JobAd ad, List<String> imageUrls) {
        JobAdResponse response = new JobAdResponse();

        setCommonResponseFields(response, ad, "JOB", imageUrls);

        response.setCategory(ad.getCategory());
        response.setWorkSchedule(ad.getWorkSchedule());
        response.setExperienceRequired(ad.getExperienceRequired());
        response.setEducationLevel(ad.getEducationLevel());
        response.setSalary(ad.getSalary());

        return response;
    }

    // ==================== HELPER METHODS ====================

    private void setCommonFields(BaseAd ad, java.math.BigDecimal price,
                                 com.example.bazarxplatform.Enums.City city,
                                 String description, String contactName,
                                 String contactPhone, String contactEmail, User user) {
        ad.setPrice(price);
        ad.setCity(city);
        ad.setDescription(description);

        // Contact info - default to user's info if not provided
        ad.setContactName(contactName != null ? contactName :
                user.getFirstName() + " " + user.getLastName());
        ad.setContactPhone(contactPhone != null ? contactPhone : user.getPhoneNumber());
        ad.setContactEmail(contactEmail != null ? contactEmail : user.getEmail());
    }

    private void setCommonResponseFields(AdResponse response, BaseAd ad,
                                         String adType, List<String> imageUrls) {
        response.setId(ad.getId());
        response.setAdType(adType);

        // User info
        response.setUserId(ad.getUser().getId());
        response.setUsername(ad.getUser().getUsername());

        // Status
        response.setStatus(ad.getStatus());

        // Contact
        response.setContactName(ad.getContactName());
        response.setContactPhone(ad.getContactPhone());
        response.setContactEmail(ad.getContactEmail());

        // Common fields
        response.setPrice(ad.getPrice());
        response.setCity(ad.getCity());
        response.setDescription(ad.getDescription());

        // Featured
        response.setIsSearchFeatured(ad.getSearchFeaturedUntil() != null &&
                ad.getSearchFeaturedUntil().isAfter(LocalDateTime.now()));
        response.setIsCategoryFeatured(ad.getCategoryFeaturedUntil() != null &&
                ad.getCategoryFeaturedUntil().isAfter(LocalDateTime.now()));
        response.setIsAppFeatured(ad.getAppFeaturedUntil() != null &&
                ad.getAppFeaturedUntil().isAfter(LocalDateTime.now()));
        response.setSearchFeaturedUntil(ad.getSearchFeaturedUntil());
        response.setCategoryFeaturedUntil(ad.getCategoryFeaturedUntil());
        response.setAppFeaturedUntil(ad.getAppFeaturedUntil());

        // Timestamps
        response.setCreatedAt(ad.getCreatedAt());
        response.setPublishedAt(ad.getPublishedAt());
        response.setExpiresAt(ad.getExpiresAt());

        // Stats
        response.setViewCount(ad.getViewCount());

        // Images
        response.setImageUrls(imageUrls);
        if (imageUrls != null && !imageUrls.isEmpty()) {
            response.setPrimaryImageUrl(imageUrls.get(0));
        }
    }

    // Generic method for any ad type
    public AdResponse toAdResponse(BaseAd ad, List<String> imageUrls) {
        if (ad instanceof CarAd) {
            return toCarAdResponse((CarAd) ad, imageUrls);
        } else if (ad instanceof PhoneAd) {
            return toPhoneAdResponse((PhoneAd) ad, imageUrls);
        } else if (ad instanceof HomeAd) {
            return toHomeAdResponse((HomeAd) ad, imageUrls);
        } else if (ad instanceof JobAd) {
            return toJobAdResponse((JobAd) ad, imageUrls);
        }
        return null;
    }
}
