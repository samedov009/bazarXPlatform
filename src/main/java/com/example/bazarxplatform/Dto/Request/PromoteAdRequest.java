package com.example.bazarxplatform.Dto.Request;

import com.example.bazarxplatform.Enums.PromotionType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoteAdRequest {

    @NotNull(message = "Önə çıxarma növü seçilməlidir")
    private PromotionType promotionType;

    @NotNull(message = "Kart ID-si daxil edilməlidir")
    private Long cardId;

    @NotBlank(message = "CVV daxil edilməlidir")
    @Pattern(regexp = "\\d{3}", message = "CVV 3 rəqəm olmalıdır")
    private String cvv;
}