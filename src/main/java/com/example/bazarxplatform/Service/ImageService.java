package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Entity.AdImage;
import com.example.bazarxplatform.Entity.BaseAd;
import com.example.bazarxplatform.Exception.ResourceNotFoundException;
import com.example.bazarxplatform.Repository.AdImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AdImageRepository adImageRepository;
    private final AdService adService;

    private static final String UPLOAD_DIR = "uploads/images/";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif", "webp");

    // Upload image
    @Transactional
    public String uploadImage(MultipartFile file, Long adId, Boolean isPrimary, Long userId) {
        BaseAd ad = adService.getAdEntityById(adId);

        // Check ownership
        if (!ad.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu elana şəkil əlavə etmək üçün icazəniz yoxdur");
        }

        // Validate file
        validateFile(file);

        // Create upload directory if not exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String filename = UUID.randomUUID().toString() + "." + extension;
        String filepath = UPLOAD_DIR + filename;

        try {
            // Save file
            Path path = Paths.get(filepath);
            Files.write(path, file.getBytes());

            // If this is primary, unset other primary images
            if (isPrimary) {
                List<AdImage> existingImages = adImageRepository.findByAdId(adId);
                existingImages.forEach(img -> {
                    img.setIsPrimary(false);
                    adImageRepository.save(img);
                });
            }

            // Save to database
            AdImage adImage = new AdImage();
            adImage.setAd(ad);
            adImage.setImageUrl("/" + filepath);
            adImage.setIsPrimary(isPrimary);

            adImageRepository.save(adImage);

            return "/" + filepath;

        } catch (IOException e) {
            throw new RuntimeException("Şəkil yüklənərkən xəta baş verdi: " + e.getMessage());
        }
    }

    // Get ad images
    public List<String> getAdImages(Long adId) {
        return adImageRepository.findByAdId(adId)
                .stream()
                .sorted((img1, img2) -> {
                    if (img1.getIsPrimary() && !img2.getIsPrimary()) return -1;
                    if (!img1.getIsPrimary() && img2.getIsPrimary()) return 1;
                    return 0;
                })
                .map(AdImage::getImageUrl)
                .toList();
    }

    // Delete image
    @Transactional
    public void deleteImage(Long imageId, Long userId) {
        AdImage image = adImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Şəkil tapılmadı"));

        // Check ownership
        if (!image.getAd().getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu şəkli silmək üçün icazəniz yoxdur");
        }

        // Delete file from disk
        try {
            Path path = Paths.get(image.getImageUrl().substring(1)); // Remove leading "/"
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // Log error but continue
            System.err.println("Failed to delete file: " + e.getMessage());
        }

        // Delete from database
        adImageRepository.delete(image);
    }

    // Set primary image
    @Transactional
    public void setPrimaryImage(Long imageId, Long userId) {
        AdImage image = adImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Şəkil tapılmadı"));

        // Check ownership
        if (!image.getAd().getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu əməliyyatı yerinə yetirmək üçün icazəniz yoxdur");
        }

        // Unset other primary images for this ad
        List<AdImage> adImages = adImageRepository.findByAdId(image.getAd().getId());
        adImages.forEach(img -> {
            img.setIsPrimary(false);
            adImageRepository.save(img);
        });

        // Set this as primary
        image.setIsPrimary(true);
        adImageRepository.save(image);
    }

    // Delete all images for an ad
    @Transactional
    public void deleteAdImages(Long adId, Long userId) {
        BaseAd ad = adService.getAdEntityById(adId);

        // Check ownership
        if (!ad.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu əməliyyatı yerinə yetirmək üçün icazəniz yoxdur");
        }

        List<AdImage> images = adImageRepository.findByAdId(adId);

        // Delete files from disk
        images.forEach(img -> {
            try {
                Path path = Paths.get(img.getImageUrl().substring(1));
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + e.getMessage());
            }
        });

        // Delete from database
        adImageRepository.deleteByAdId(adId);
    }

    // Validate file
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Fayl boşdur");
        }

        // Check size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("Fayl həcmi maksimum 5MB olmalıdır");
        }

        // Check extension
        String filename = file.getOriginalFilename();
        String extension = getFileExtension(filename);

        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new RuntimeException("Yalnız şəkil faylları qəbul edilir (jpg, jpeg, png, gif, webp)");
        }
    }

    // Get file extension
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
