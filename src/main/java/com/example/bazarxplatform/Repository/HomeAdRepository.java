package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.HomeAd;
import com.example.bazarxplatform.Enums.AdStatus;
import com.example.bazarxplatform.Enums.AdType;
import com.example.bazarxplatform.Enums.City;
import com.example.bazarxplatform.Enums.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HomeAdRepository extends JpaRepository<HomeAd, Long> {

    List<HomeAd> findByStatus(AdStatus status);

    List<HomeAd> findByCity(City city);

    List<HomeAd> findByStatusAndCity(AdStatus status, City city);

    List<HomeAd> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<HomeAd> findByUserId(Long userId);

    List<HomeAd> findByPropertyType(PropertyType propertyType);

    List<HomeAd> findByAdType(AdType adType);

    List<HomeAd> findByRoomCount(Integer roomCount);
}