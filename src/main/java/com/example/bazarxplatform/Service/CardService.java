package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Dto.Request.AddCardRequest;
import com.example.bazarxplatform.Dto.Response.CardResponse;
import com.example.bazarxplatform.Entity.Card;
import com.example.bazarxplatform.Entity.User;
import com.example.bazarxplatform.Exception.ResourceNotFoundException;
import com.example.bazarxplatform.Mapper.CardMapper;
import com.example.bazarxplatform.Repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserService userService;
    private final CardMapper cardMapper;

    // Add card
    @Transactional
    public CardResponse addCard(AddCardRequest request, Long userId) {
        User user = userService.getUserEntityById(userId);

        // If this is the first card or marked as default, set it as default
        List<Card> existingCards = cardRepository.findByUserId(userId);
        if (existingCards.isEmpty() || request.getIsDefault()) {
            // Unset other default cards
            existingCards.forEach(card -> {
                card.setIsDefault(false);
                cardRepository.save(card);
            });
        }

        Card card = cardMapper.toEntity(request, user);
        Card savedCard = cardRepository.save(card);

        return cardMapper.toResponse(savedCard);
    }

    // Get user's cards
    public List<CardResponse> getUserCards(Long userId) {
        return cardRepository.findByUserId(userId)
                .stream()
                .map(cardMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get card by ID
    public Card getCardEntityById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Kart tapılmadı"));
    }

    // Set default card
    @Transactional
    public void setDefaultCard(Long cardId, Long userId) {
        Card card = getCardEntityById(cardId);

        // Check ownership
        if (!card.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu kart sizə aid deyil");
        }

        // Unset other default cards
        cardRepository.findByUserId(userId).forEach(c -> {
            c.setIsDefault(false);
            cardRepository.save(c);
        });

        // Set this card as default
        card.setIsDefault(true);
        cardRepository.save(card);
    }

    // Delete card
    @Transactional
    public void deleteCard(Long cardId, Long userId) {
        Card card = getCardEntityById(cardId);

        // Check ownership
        if (!card.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu kart sizə aid deyil");
        }

        cardRepository.delete(card);
    }

    // Validate CVV (simple validation for demo)
    public boolean validateCVV(Long cardId, String cvv) {
        // In real app, this would verify with payment gateway
        // For demo, just check if it's 3 digits
        return cvv != null && cvv.matches("\\d{3}");
    }
}
