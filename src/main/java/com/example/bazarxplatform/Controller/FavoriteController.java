package com.example.bazarxplatform.Controller;

import com.example.bazarxplatform.Dto.Response.FavoriteResponse;
import com.example.bazarxplatform.Service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;

    // Add to favorites
    @PostMapping("/{adId}")
    public ResponseEntity<FavoriteResponse> addToFavorites(
            @PathVariable Long adId,
            @RequestParam Long userId) {
        FavoriteResponse response = favoriteService.addToFavorites(adId, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Remove from favorites
    @DeleteMapping("/{adId}")
    public ResponseEntity<String> removeFromFavorites(
            @PathVariable Long adId,
            @RequestParam Long userId) {
        favoriteService.removeFromFavorites(adId, userId);
        return ResponseEntity.ok("Seçilmişlərdən silindi");
    }

    // Get user's favorites
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteResponse>> getUserFavorites(@PathVariable Long userId) {
        List<FavoriteResponse> favorites = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    // Check if ad is in favorites
    @GetMapping("/check")
    public ResponseEntity<Boolean> isInFavorites(
            @RequestParam Long adId,
            @RequestParam Long userId) {
        boolean isInFavorites = favoriteService.isInFavorites(adId, userId);
        return ResponseEntity.ok(isInFavorites);
    }

    // Get favorites count
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getFavoritesCount(@PathVariable Long userId) {
        long count = favoriteService.getFavoritesCount(userId);
        return ResponseEntity.ok(count);
    }
}