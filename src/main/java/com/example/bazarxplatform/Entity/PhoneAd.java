package com.example.bazarxplatform.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "phone_ads")
@Data
@EqualsAndHashCode(callSuper = true)
public class PhoneAd extends BaseAd {

    private String brand;
    private Boolean isNew = false;
    private Boolean hasDelivery = false;
}