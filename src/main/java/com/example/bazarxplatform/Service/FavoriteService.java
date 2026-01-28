package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Dto.Response.FavoriteResponse;
import com.example.bazarxplatform.Entity.BaseAd;
import com.example.bazarxplatform.Entity.Favorite;
import com.example.bazarxplatform.Entity.User;
import com.example.bazarxplatform.Exception.ResourceNotFoundException;
import com.example.bazarxplatform.Mapper.FavoriteMapper;
import com.example.bazarxplatform.Repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final AdService adService;
    private final FavoriteMapper favoriteMapper;

    // Add to favorites
    @Transactional
    public FavoriteResponse addToFavorites(Long adId, Long userId) {
        User user = userService.getUserEntityById(userId);
        BaseAd ad = adService.getAdEntityById(adId);

        // Check if already in favorites
        if (favoriteRepository.existsByUserIdAndAdId(userId, adId)) {
            throw new RuntimeException("Bu elan artıq seçilmişlərdədir");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setAd(ad);

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return favoriteMapper.toResponse(savedFavorite);
    }

    // Remove from favorites
    @Transactional
    public void removeFromFavorites(Long adId, Long userId) {
        if (!favoriteRepository.existsByUserIdAndAdId(userId, adId)) {
            throw new ResourceNotFoundException("Bu elan seçilmişlərdə tapılmadı");
        }

        favoriteRepository.deleteByUserIdAndAdId(userId, adId);
    }

    // Get user's favorites
    public List<FavoriteResponse> getUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId)
                .stream()
                .map(favoriteMapper::toResponse)
                .sorted((f1, f2) -> f2.getAddedAt().compareTo(f1.getAddedAt()))
                .collect(Collectors.toList());
    }

    // Check if ad is in favorites
    public boolean isInFavorites(Long adId, Long userId) {
        return favoriteRepository.existsByUserIdAndAdId(userId, adId);
    }

    // Get favorites count for user
    public long getFavoritesCount(Long userId) {
        return favoriteRepository.findByUserId(userId).size();
    }
}
