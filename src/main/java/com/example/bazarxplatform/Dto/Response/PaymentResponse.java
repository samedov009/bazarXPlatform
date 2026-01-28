package com.example.bazarxplatform.Dto.Response;

import com.example.bazarxplatform.Enums.PaymentStatus;
import com.example.bazarxplatform.Enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private Long userId;
    private String maskedCardNumber;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentStatus status;
    private String description;
    private LocalDateTime createdAt;
}