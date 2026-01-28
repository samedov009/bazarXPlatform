package com.example.bazarxplatform.Entity;


import com.example.bazarxplatform.Enums.AdType;
import com.example.bazarxplatform.Enums.PropertyType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "home_ads")
@Data
@EqualsAndHashCode(callSuper = true)
public class HomeAd extends BaseAd {

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private AdType adType;

    private Integer area; // m²
    private Integer roomCount;
    private String location; // address
}
