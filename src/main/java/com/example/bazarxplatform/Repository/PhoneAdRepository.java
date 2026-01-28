package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.PhoneAd;
import com.example.bazarxplatform.Enums.AdStatus;
import com.example.bazarxplatform.Enums.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PhoneAdRepository extends JpaRepository<PhoneAd, Long> {

    List<PhoneAd> findByStatus(AdStatus status);

    List<PhoneAd> findByCity(City city);

    List<PhoneAd> findByStatusAndCity(AdStatus status, City city);

    List<PhoneAd> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<PhoneAd> findByUserId(Long userId);

    List<PhoneAd> findByBrandContainingIgnoreCase(String brand);

    List<PhoneAd> findByIsNew(Boolean isNew);
}
