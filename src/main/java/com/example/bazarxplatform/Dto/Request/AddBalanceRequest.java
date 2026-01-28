package com.example.bazarxplatform.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBalanceRequest {

    @NotNull(message = "Kart ID-si daxil edilməlidir")
    private Long cardId;

    @NotNull(message = "Məbləğ daxil edilməlidir")
    @DecimalMin(value = "1.00", message = "Minimum məbləğ 1 AZN olmalıdır")
    @DecimalMax(value = "10000.00", message = "Maksimum məbləğ 10000 AZN olmalıdır")
    private BigDecimal amount;

    @NotBlank(message = "CVV daxil edilməlidir")
    @Pattern(regexp = "\\d{3}", message = "CVV 3 rəqəm olmalıdır")
    private String cvv;
}