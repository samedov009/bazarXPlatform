package com.example.bazarxplatform.Mapper;

import com.example.bazarxplatform.Dto.Request.AddCardRequest;
import com.example.bazarxplatform.Dto.Response.CardResponse;
import com.example.bazarxplatform.Entity.Card;
import com.example.bazarxplatform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    // Request -> Entity
    public Card toEntity(AddCardRequest request, User user) {
        if (request == null) {
            return null;
        }

        Card card = new Card();
        card.setUser(user);

        // Mask card number (store only last 4 digits)
        card.setCardNumber(maskCardNumber(request.getCardNumber()));
        card.setCardHolderName(request.getCardHolderName());
        card.setExpiryMonth(request.getExpiryMonth());
        card.setExpiryYear(request.getExpiryYear());
        card.setIsDefault(request.getIsDefault());

        // CVV is NOT stored!

        return card;
    }

    // Entity -> Response
    public CardResponse toResponse(Card card) {
        if (card == null) {
            return null;
        }

        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setMaskedCardNumber(formatCardNumber(card.getCardNumber()));
        response.setCardHolderName(card.getCardHolderName());
        response.setExpiryMonth(card.getExpiryMonth());
        response.setExpiryYear(card.getExpiryYear());
        response.setIsDefault(card.getIsDefault());
        response.setCreatedAt(card.getCreatedAt());

        return response;
    }

    // Helper: Mask card number (keep only last 4 digits)
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }
        return cardNumber.substring(cardNumber.length() - 4);
    }

    // Helper: Format for display
    private String formatCardNumber(String lastFourDigits) {
        return "**** **** **** " + lastFourDigits;
    }
}