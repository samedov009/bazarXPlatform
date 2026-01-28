package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.CarAd;
import com.example.bazarxplatform.Enums.AdStatus;
import com.example.bazarxplatform.Enums.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarAdRepository extends JpaRepository<CarAd, Long> {

    List<CarAd> findByStatus(AdStatus status);

    List<CarAd> findByCity(City city);

    List<CarAd> findByStatusAndCity(AdStatus status, City city);

    List<CarAd> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<CarAd> findByUserId(Long userId);

    List<CarAd> findByBrandContainingIgnoreCase(String brand);
}
