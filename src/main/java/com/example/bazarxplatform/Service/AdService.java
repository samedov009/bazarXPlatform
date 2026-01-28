package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Dto.Request.*;
import com.example.bazarxplatform.Dto.Response.*;
import com.example.bazarxplatform.Entity.*;
import com.example.bazarxplatform.Enums.*;
import com.example.bazarxplatform.Exception.ResourceNotFoundException;
import com.example.bazarxplatform.Mapper.AdMapper;
import com.example.bazarxplatform.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdService {

    private final CarAdRepository carAdRepository;
    private final PhoneAdRepository phoneAdRepository;
    private final HomeAdRepository homeAdRepository;
    private final JobAdRepository jobAdRepository;
    private final AdImageRepository adImageRepository;
    private final UserService userService;
    private final AdMapper adMapper;

    // ==================== CREATE ADS ====================

    @Transactional
    public CarAdResponse createCarAd(CreateCarAdRequest request, Long userId) {
        User user = userService.getUserEntityById(userId);
        CarAd ad = adMapper.toCarAdEntity(request, user);
        CarAd savedAd = carAdRepository.save(ad);
        return adMapper.toCarAdResponse(savedAd, getAdImageUrls(savedAd.getId()));
    }

    @Transactional
    public PhoneAdResponse createPhoneAd(CreatePhoneAdRequest request, Long userId) {
        User user = userService.getUserEntityById(userId);
        PhoneAd ad = adMapper.toPhoneAdEntity(request, user);
        PhoneAd savedAd = phoneAdRepository.save(ad);
        return adMapper.toPhoneAdResponse(savedAd, getAdImageUrls(savedAd.getId()));
    }

    @Transactional
    public HomeAdResponse createHomeAd(CreateHomeAdRequest request, Long userId) {
        User user = userService.getUserEntityById(userId);
        HomeAd ad = adMapper.toHomeAdEntity(request, user);
        HomeAd savedAd = homeAdRepository.save(ad);
        return adMapper.toHomeAdResponse(savedAd, getAdImageUrls(savedAd.getId()));
    }

    @Transactional
    public JobAdResponse createJobAd(CreateJobAdRequest request, Long userId) {
        User user = userService.getUserEntityById(userId);
        JobAd ad = adMapper.toJobAdEntity(request, user);
        JobAd savedAd = jobAdRepository.save(ad);
        return adMapper.toJobAdResponse(savedAd, getAdImageUrls(savedAd.getId()));
    }

    // ==================== GET ADS ====================

    // Get all active ads (all types)
    public List<AdResponse> getAllActiveAds() {
        List<AdResponse> allAds = List.of();

        // Collect all active ads from all repositories
        List<CarAdResponse> carAds = carAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toCarAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        List<PhoneAdResponse> phoneAds = phoneAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toPhoneAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        List<HomeAdResponse> homeAds = homeAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toHomeAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        List<JobAdResponse> jobAds = jobAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toJobAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        // Combine all
        allAds = List.of(carAds, phoneAds, homeAds, jobAds)
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // Sort by featured and creation date
        return allAds.stream()
                .sorted((a1, a2) -> {
                    // App featured first
                    if (a1.getIsAppFeatured() && !a2.getIsAppFeatured()) return -1;
                    if (!a1.getIsAppFeatured() && a2.getIsAppFeatured()) return 1;

                    // Then by creation date (newest first)
                    return a2.getCreatedAt().compareTo(a1.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Get car ads
    public List<CarAdResponse> getCarAds() {
        return carAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toCarAdResponse(ad, getAdImageUrls(ad.getId())))
                .sorted((a1, a2) -> {
                    // Category featured first
                    if (a1.getIsCategoryFeatured() && !a2.getIsCategoryFeatured()) return -1;
                    if (!a1.getIsCategoryFeatured() && a2.getIsCategoryFeatured()) return 1;
                    return a2.getCreatedAt().compareTo(a1.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Get phone ads
    public List<PhoneAdResponse> getPhoneAds() {
        return phoneAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toPhoneAdResponse(ad, getAdImageUrls(ad.getId())))
                .sorted((a1, a2) -> {
                    if (a1.getIsCategoryFeatured() && !a2.getIsCategoryFeatured()) return -1;
                    if (!a1.getIsCategoryFeatured() && a2.getIsCategoryFeatured()) return 1;
                    return a2.getCreatedAt().compareTo(a1.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Get home ads
    public List<HomeAdResponse> getHomeAds() {
        return homeAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toHomeAdResponse(ad, getAdImageUrls(ad.getId())))
                .sorted((a1, a2) -> {
                    if (a1.getIsCategoryFeatured() && !a2.getIsCategoryFeatured()) return -1;
                    if (!a1.getIsCategoryFeatured() && a2.getIsCategoryFeatured()) return 1;
                    return a2.getCreatedAt().compareTo(a1.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Get job ads
    public List<JobAdResponse> getJobAds() {
        return jobAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .map(ad -> adMapper.toJobAdResponse(ad, getAdImageUrls(ad.getId())))
                .sorted((a1, a2) -> {
                    if (a1.getIsCategoryFeatured() && !a2.getIsCategoryFeatured()) return -1;
                    if (!a1.getIsCategoryFeatured() && a2.getIsCategoryFeatured()) return 1;
                    return a2.getCreatedAt().compareTo(a1.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Get user's ads
    public List<AdResponse> getUserAds(Long userId) {
        List<CarAdResponse> carAds = carAdRepository.findByUserId(userId)
                .stream()
                .map(ad -> adMapper.toCarAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        List<PhoneAdResponse> phoneAds = phoneAdRepository.findByUserId(userId)
                .stream()
                .map(ad -> adMapper.toPhoneAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        List<HomeAdResponse> homeAds = homeAdRepository.findByUserId(userId)
                .stream()
                .map(ad -> adMapper.toHomeAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        List<JobAdResponse> jobAds = jobAdRepository.findByUserId(userId)
                .stream()
                .map(ad -> adMapper.toJobAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());

        return List.of(carAds, phoneAds, homeAds, jobAds)
                .stream()
                .flatMap(List::stream)
                .sorted((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    // ==================== SEARCH & FILTER ====================

    // Search car ads by brand
    public List<CarAdResponse> searchCarsByBrand(String brand) {
        return carAdRepository.findByBrandContainingIgnoreCase(brand)
                .stream()
                .filter(ad -> ad.getStatus() == AdStatus.ACTIVE)
                .map(ad -> adMapper.toCarAdResponse(ad, getAdImageUrls(ad.getId())))
                .sorted((a1, a2) -> {
                    // Search featured first
                    if (a1.getIsSearchFeatured() && !a2.getIsSearchFeatured()) return -1;
                    if (!a1.getIsSearchFeatured() && a2.getIsSearchFeatured()) return 1;
                    return a2.getCreatedAt().compareTo(a1.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Search phone ads by brand
    public List<PhoneAdResponse> searchPhonesByBrand(String brand) {
        return phoneAdRepository.findByBrandContainingIgnoreCase(brand)
                .stream()
                .filter(ad -> ad.getStatus() == AdStatus.ACTIVE)
                .map(ad -> adMapper.toPhoneAdResponse(ad, getAdImageUrls(ad.getId())))
                .sorted((a1, a2) -> {
                    if (a1.getIsSearchFeatured() && !a2.getIsSearchFeatured()) return -1;
                    if (!a1.getIsSearchFeatured() && a2.getIsSearchFeatured()) return 1;
                    return a2.getCreatedAt().compareTo(a1.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Filter by city
    public List<CarAdResponse> filterCarsByCity(City city) {
        return carAdRepository.findByStatusAndCity(AdStatus.ACTIVE, city)
                .stream()
                .map(ad -> adMapper.toCarAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());
    }

    // Filter by price range
    public List<CarAdResponse> filterCarsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return carAdRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .filter(ad -> ad.getStatus() == AdStatus.ACTIVE)
                .map(ad -> adMapper.toCarAdResponse(ad, getAdImageUrls(ad.getId())))
                .collect(Collectors.toList());
    }

    // ==================== UPDATE & DELETE ====================

    // Archive ad
    @Transactional
    public void archiveAd(Long adId, Long userId) {
        BaseAd ad = findAdById(adId);

        // Check ownership
        if (!ad.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu elanı yalnız sahibi arxivləşdirə bilər");
        }

        ad.setStatus(AdStatus.ARCHIVED);
        saveAd(ad);
    }

    // Delete ad
    @Transactional
    public void deleteAd(Long adId, Long userId) {
        BaseAd ad = findAdById(adId);

        // Check ownership
        if (!ad.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu elanı yalnız sahibi silə bilər");
        }

        // Delete images first
        adImageRepository.deleteByAdId(adId);

        // Delete ad
        deleteAdFromRepository(ad);
    }

    // Increment view count
    @Transactional
    public void incrementViewCount(Long adId) {
        BaseAd ad = findAdById(adId);
        ad.setViewCount(ad.getViewCount() + 1);
        saveAd(ad);
    }

    // ==================== HELPER METHODS ====================

    private List<String> getAdImageUrls(Long adId) {
        return adImageRepository.findByAdId(adId)
                .stream()
                .sorted((img1, img2) -> {
                    if (img1.getIsPrimary() && !img2.getIsPrimary()) return -1;
                    if (!img1.getIsPrimary() && img2.getIsPrimary()) return 1;
                    return 0;
                })
                .map(AdImage::getImageUrl)
                .collect(Collectors.toList());
    }

    private BaseAd findAdById(Long adId) {
        // Try each repository
        return carAdRepository.findById(adId)
                .map(ad -> (BaseAd) ad)
                .or(() -> phoneAdRepository.findById(adId).map(ad -> (BaseAd) ad))
                .or(() -> homeAdRepository.findById(adId).map(ad -> (BaseAd) ad))
                .or(() -> jobAdRepository.findById(adId).map(ad -> (BaseAd) ad))
                .orElseThrow(() -> new ResourceNotFoundException("Elan tapılmadı"));
    }

    private void saveAd(BaseAd ad) {
        if (ad instanceof CarAd) {
            carAdRepository.save((CarAd) ad);
        } else if (ad instanceof PhoneAd) {
            phoneAdRepository.save((PhoneAd) ad);
        } else if (ad instanceof HomeAd) {
            homeAdRepository.save((HomeAd) ad);
        } else if (ad instanceof JobAd) {
            jobAdRepository.save((JobAd) ad);
        }
    }

    private void deleteAdFromRepository(BaseAd ad) {
        if (ad instanceof CarAd) {
            carAdRepository.delete((CarAd) ad);
        } else if (ad instanceof PhoneAd) {
            phoneAdRepository.delete((PhoneAd) ad);
        } else if (ad instanceof HomeAd) {
            homeAdRepository.delete((HomeAd) ad);
        } else if (ad instanceof JobAd) {
            jobAdRepository.delete((JobAd) ad);
        }
    }

    // Get ad entity (for internal use)
    public BaseAd getAdEntityById(Long adId) {
        return findAdById(adId);
    }
}
