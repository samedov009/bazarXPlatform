package com.example.bazarxplatform.Dto.Request;

import com.example.bazarxplatform.Enums.AdType;
import com.example.bazarxplatform.Enums.City;
import com.example.bazarxplatform.Enums.PropertyType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateHomeAdRequest {

    // Home specific fields
    @NotNull(message = "Əmlak növü seçilməlidir")
    private PropertyType propertyType;

    @NotNull(message = "Elan tipi seçilməlidir (Satış/Kirayə)")
    private AdType adType;

    @NotNull(message = "Sahə daxil edilməlidir")
    @Min(value = 1, message = "Sahə minimum 1 m² olmalıdır")
    private Integer area;

    @NotNull(message = "Otaq sayı daxil edilməlidir")
    @Min(value = 0, message = "Otaq sayı minimum 0 olmalıdır")
    private Integer roomCount;

    @NotBlank(message = "Ünvan daxil edilməlidir")
    private String location;

    // Common fields
    @NotNull(message = "Qiymət daxil edilməlidir")
    @DecimalMin(value = "0.01", message = "Qiymət 0-dan böyük olmalıdır")
    private BigDecimal price;

    @NotNull(message = "Şəhər seçilməlidir")
    private City city;

    @NotBlank(message = "Təsvir daxil edilməlidir")
    @Size(min = 10, max = 5000, message = "Təsvir 10-5000 simvol arası olmalıdır")
    private String description;

    // Contact info (optional)
    private String contactName;
    private String contactPhone;
    private String contactEmail;
}
