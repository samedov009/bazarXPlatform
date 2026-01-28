package com.example.bazarxplatform.Entity;

import com.example.bazarxplatform.Enums.BodyType;
import com.example.bazarxplatform.Enums.Color;
import com.example.bazarxplatform.Enums.FuelType;
import com.example.bazarxplatform.Enums.Transmission;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "car_ads")
@Data
@EqualsAndHashCode(callSuper = true)
public class CarAd extends BaseAd {

    private String brand;

    @Enumerated(EnumType.STRING)
    private Color color;

    private Double engineVolume;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    private Integer year;
    private Integer mileage;
    private Boolean isNew = false;
}