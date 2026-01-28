package com.example.bazarxplatform.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PhoneAdResponse extends AdResponse {

    private String brand;
    private Boolean isNew;
    private Boolean hasDelivery;
}