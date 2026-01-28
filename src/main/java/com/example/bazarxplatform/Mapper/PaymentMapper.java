package com.example.bazarxplatform.Mapper;

import com.example.bazarxplatform.Dto.Response.PaymentResponse;
import com.example.bazarxplatform.Entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    // Entity -> Response
    public PaymentResponse toResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setUserId(payment.getUser().getId());

        // Masked card number
        if (payment.getCard() != null) {
            response.setMaskedCardNumber("**** **** **** " + payment.getCard().getCardNumber());
        }

        response.setAmount(payment.getAmount());
        response.setPaymentType(payment.getPaymentType());
        response.setStatus(payment.getStatus());
        response.setDescription(payment.getDescription());
        response.setCreatedAt(payment.getCreatedAt());

        return response;
    }
}