package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.AdImage;
import com.example.bazarxplatform.Entity.BaseAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdImageRepository extends JpaRepository<AdImage, Long> {

    List<AdImage> findByAd(BaseAd ad);

    List<AdImage> findByAdId(Long adId);

    Optional<AdImage> findByAdAndIsPrimary(BaseAd ad, Boolean isPrimary);

    void deleteByAdId(Long adId);
}