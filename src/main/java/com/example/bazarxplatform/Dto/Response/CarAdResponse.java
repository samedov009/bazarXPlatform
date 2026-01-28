package com.example.bazarxplatform.Dto.Response;

import com.example.bazarxplatform.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CarAdResponse extends AdResponse {

    private String brand;
    private Color color;
    private Double engineVolume;
    private FuelType fuelType;
    private Transmission transmission;
    private BodyType bodyType;
    private Integer year;
    private Integer mileage;
    private Boolean isNew;
}
