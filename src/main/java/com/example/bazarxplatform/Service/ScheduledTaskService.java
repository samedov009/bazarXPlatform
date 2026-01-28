package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Entity.*;
import com.example.bazarxplatform.Enums.AdStatus;
import com.example.bazarxplatform.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskService {

    private final CarAdRepository carAdRepository;
    private final PhoneAdRepository phoneAdRepository;
    private final HomeAdRepository homeAdRepository;
    private final JobAdRepository jobAdRepository;

    // Auto-publish ads (PENDING -> ACTIVE after 20 seconds)
    @Scheduled(fixedDelay = 10000) // Runs every 10 seconds
    @Transactional
    public void autoPublishAds() {
        LocalDateTime threshold = LocalDateTime.now().minusSeconds(20);

        List<BaseAd> adsToPublish = new ArrayList<>();

        // Find all PENDING ads older than 20 seconds
        adsToPublish.addAll(carAdRepository.findByStatus(AdStatus.PENDING)
                .stream()
                .filter(ad -> ad.getCreatedAt().isBefore(threshold))
                .toList());

        adsToPublish.addAll(phoneAdRepository.findByStatus(AdStatus.PENDING)
                .stream()
                .filter(ad -> ad.getCreatedAt().isBefore(threshold))
                .toList());

        adsToPublish.addAll(homeAdRepository.findByStatus(AdStatus.PENDING)
                .stream()
                .filter(ad -> ad.getCreatedAt().isBefore(threshold))
                .toList());

        adsToPublish.addAll(jobAdRepository.findByStatus(AdStatus.PENDING)
                .stream()
                .filter(ad -> ad.getCreatedAt().isBefore(threshold))
                .toList());

        // Publish ads
        adsToPublish.forEach(ad -> {
            ad.setStatus(AdStatus.ACTIVE);
            ad.setPublishedAt(LocalDateTime.now());
            ad.setExpiresAt(LocalDateTime.now().plusDays(30)); // 30 days from now

            // Save based on type
            if (ad instanceof CarAd) {
                carAdRepository.save((CarAd) ad);
            } else if (ad instanceof PhoneAd) {
                phoneAdRepository.save((PhoneAd) ad);
            } else if (ad instanceof HomeAd) {
                homeAdRepository.save((HomeAd) ad);
            } else if (ad instanceof JobAd) {
                jobAdRepository.save((JobAd) ad);
            }
        });

        if (!adsToPublish.isEmpty()) {
            log.info("Auto-published {} ads", adsToPublish.size());
        }
    }

    // Auto-expire ads (ACTIVE -> EXPIRED after 30 days)
    @Scheduled(cron = "0 0 2 * * *") // Runs every day at 2 AM
    @Transactional
    public void autoExpireAds() {
        LocalDateTime now = LocalDateTime.now();

        List<BaseAd> adsToExpire = new ArrayList<>();

        // Find all ACTIVE ads that have expired
        adsToExpire.addAll(carAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .filter(ad -> ad.getExpiresAt() != null && ad.getExpiresAt().isBefore(now))
                .toList());

        adsToExpire.addAll(phoneAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .filter(ad -> ad.getExpiresAt() != null && ad.getExpiresAt().isBefore(now))
                .toList());

        adsToExpire.addAll(homeAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .filter(ad -> ad.getExpiresAt() != null && ad.getExpiresAt().isBefore(now))
                .toList());

        adsToExpire.addAll(jobAdRepository.findByStatus(AdStatus.ACTIVE)
                .stream()
                .filter(ad -> ad.getExpiresAt() != null && ad.getExpiresAt().isBefore(now))
                .toList());

        // Expire ads
        adsToExpire.forEach(ad -> {
            ad.setStatus(AdStatus.EXPIRED);

            if (ad instanceof CarAd) {
                carAdRepository.save((CarAd) ad);
            } else if (ad instanceof PhoneAd) {
                phoneAdRepository.save((PhoneAd) ad);
            } else if (ad instanceof HomeAd) {
                homeAdRepository.save((HomeAd) ad);
            } else if (ad instanceof JobAd) {
                jobAdRepository.save((JobAd) ad);
            }
        });

        if (!adsToExpire.isEmpty()) {
            log.info("Auto-expired {} ads", adsToExpire.size());
        }
    }
}
