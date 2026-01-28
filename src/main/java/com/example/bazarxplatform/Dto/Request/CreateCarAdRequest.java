package com.example.bazarxplatform.Dto.Request;

import com.example.bazarxplatform.Enums.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarAdRequest {

    // Car specific fields
    @NotBlank(message = "Marka daxil edilməlidir")
    private String brand;

    @NotNull(message = "Rəng seçilməlidir")
    private Color color;

    @NotNull(message = "Mühərrik həcmi daxil edilməlidir")
    @DecimalMin(value = "0.5", message = "Mühərrik həcmi minimum 0.5 olmalıdır")
    private Double engineVolume;

    @NotNull(message = "Yanacaq növü seçilməlidir")
    private FuelType fuelType;

    @NotNull(message = "Sürətlər qutusu seçilməlidir")
    private Transmission transmission;

    @NotNull(message = "Kuzov növü seçilməlidir")
    private BodyType bodyType;

    @NotNull(message = "Buraxılış ili daxil edilməlidir")
    @Min(value = 1900, message = "Buraxılış ili minimum 1900 olmalıdır")
    @Max(value = 2026, message = "Buraxılış ili maksimum 2026 olmalıdır")
    private Integer year;

    @NotNull(message = "Yürüş daxil edilməlidir")
    @Min(value = 0, message = "Yürüş minimum 0 olmalıdır")
    private Integer mileage;

    @NotNull(message = "Yeni/İşlənmiş seçilməlidir")
    private Boolean isNew;

    // Common fields from BaseAd
    @NotNull(message = "Qiymət daxil edilməlidir")
    @DecimalMin(value = "0.01", message = "Qiymət 0-dan böyük olmalıdır")
    private BigDecimal price;

    @NotNull(message = "Şəhər seçilməlidir")
    private City city;

    @NotBlank(message = "Təsvir daxil edilməlidir")
    @Size(min = 10, max = 5000, message = "Təsvir 10-5000 simvol arası olmalıdır")
    private String description;

    // Contact info (optional - defaults to user's info)
    private String contactName;
    private String contactPhone;
    private String contactEmail;
}