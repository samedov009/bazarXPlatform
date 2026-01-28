package com.example.bazarxplatform.Dto.Response;

import com.example.bazarxplatform.Enums.AdType;
import com.example.bazarxplatform.Enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HomeAdResponse extends AdResponse {

    private PropertyType propertyType;
    private AdType propertyAdType;
    private Integer area;
    private Integer roomCount;
    private String location;
}