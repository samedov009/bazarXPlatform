package com.example.bazarxplatform.Dto.Request;

import com.example.bazarxplatform.Enums.City;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePhoneAdRequest {

    // Phone specific fields
    @NotBlank(message = "Marka daxil edilməlidir")
    private String brand;

    @NotNull(message = "Yeni/İşlənmiş seçilməlidir")
    private Boolean isNew;

    @NotNull(message = "Çatdırılma seçilməlidir")
    private Boolean hasDelivery;

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