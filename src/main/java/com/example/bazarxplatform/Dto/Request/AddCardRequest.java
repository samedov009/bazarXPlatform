package com.example.bazarxplatform.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCardRequest {

    @NotBlank(message = "Kart nömrəsi daxil edilməlidir")
    @Pattern(regexp = "\\d{16}", message = "Kart nömrəsi 16 rəqəm olmalıdır")
    private String cardNumber;

    @NotBlank(message = "Kart sahibinin adı daxil edilməlidir")
    private String cardHolderName;

    @NotNull(message = "Bitmə ayı daxil edilməlidir")
    @Min(value = 1, message = "Ay 1-12 arası olmalıdır")
    @Max(value = 12, message = "Ay 1-12 arası olmalıdır")
    private Integer expiryMonth;

    @NotNull(message = "Bitmə ili daxil edilməlidir")
    @Min(value = 2024, message = "Bitmə ili minimum 2024 olmalıdır")
    private Integer expiryYear;

    @NotBlank(message = "CVV daxil edilməlidir")
    @Pattern(regexp = "\\d{3}", message = "CVV 3 rəqəm olmalıdır")
    private String cvv; // Only for validation, NOT stored in database

    private Boolean isDefault = false;
}
