package com.example.bazarxplatform.Controller;

import com.example.bazarxplatform.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;

    // Upload image
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long adId,
            @RequestParam(defaultValue = "false") Boolean isPrimary,
            @RequestParam Long userId) {
        String imageUrl = imageService.uploadImage(file, adId, isPrimary, userId);
        return new ResponseEntity<>(imageUrl, HttpStatus.CREATED);
    }

    // Get ad images
    @GetMapping("/ad/{adId}")
    public ResponseEntity<List<String>> getAdImages(@PathVariable Long adId) {
        List<String> images = imageService.getAdImages(adId);
        return ResponseEntity.ok(images);
    }

    // Delete image
    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteImage(
            @PathVariable Long imageId,
            @RequestParam Long userId) {
        imageService.deleteImage(imageId, userId);
        return ResponseEntity.ok("Şəkil silindi");
    }

    // Set primary image
    @PutMapping("/{imageId}/set-primary")
    public ResponseEntity<String> setPrimaryImage(
            @PathVariable Long imageId,
            @RequestParam Long userId) {
        imageService.setPrimaryImage(imageId, userId);
        return ResponseEntity.ok("Əsas şəkil təyin edildi");
    }

    // Delete all images for an ad
    @DeleteMapping("/ad/{adId}")
    public ResponseEntity<String> deleteAdImages(
            @PathVariable Long adId,
            @RequestParam Long userId) {
        imageService.deleteAdImages(adId, userId);
        return ResponseEntity.ok("Bütün şəkillər silindi");
    }
}
