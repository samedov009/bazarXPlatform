package com.example.bazarxplatform.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private Long id;
    private String maskedCardNumber; // "**** **** **** 1234"
    private String cardHolderName;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Boolean isDefault;
    private LocalDateTime createdAt;
}