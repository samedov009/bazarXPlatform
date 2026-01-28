package com.example.bazarxplatform.Controller;

import com.example.bazarxplatform.Dto.Request.AddCardRequest;
import com.example.bazarxplatform.Dto.Response.CardResponse;
import com.example.bazarxplatform.Service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CardController {

    private final CardService cardService;

    // Add card
    @PostMapping
    public ResponseEntity<CardResponse> addCard(
            @Valid @RequestBody AddCardRequest request,
            @RequestParam Long userId) {
        CardResponse response = cardService.addCard(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get user's cards
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardResponse>> getUserCards(@PathVariable Long userId) {
        List<CardResponse> cards = cardService.getUserCards(userId);
        return ResponseEntity.ok(cards);
    }

    // Set default card
    @PutMapping("/{cardId}/set-default")
    public ResponseEntity<String> setDefaultCard(
            @PathVariable Long cardId,
            @RequestParam Long userId) {
        cardService.setDefaultCard(cardId, userId);
        return ResponseEntity.ok("Default kart təyin edildi");
    }

    // Delete card
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(
            @PathVariable Long cardId,
            @RequestParam Long userId) {
        cardService.deleteCard(cardId, userId);
        return ResponseEntity.ok("Kart silindi");
    }
}
