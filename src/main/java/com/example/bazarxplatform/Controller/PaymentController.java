package com.example.bazarxplatform.Controller;

import com.example.bazarxplatform.Dto.Request.AddBalanceRequest;
import com.example.bazarxplatform.Dto.Request.PromoteAdRequest;
import com.example.bazarxplatform.Dto.Response.PaymentResponse;
import com.example.bazarxplatform.Service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    // Add balance
    @PostMapping("/add-balance")
    public ResponseEntity<PaymentResponse> addBalance(
            @Valid @RequestBody AddBalanceRequest request,
            @RequestParam Long userId) {
        PaymentResponse response = paymentService.addBalance(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Promote ad
    @PostMapping("/promote/{adId}")
    public ResponseEntity<PaymentResponse> promoteAd(
            @PathVariable Long adId,
            @Valid @RequestBody PromoteAdRequest request,
            @RequestParam Long userId) {
        PaymentResponse response = paymentService.promoteAd(adId, request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get payment history
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentHistory(@PathVariable Long userId) {
        List<PaymentResponse> payments = paymentService.getUserPaymentHistory(userId);
        return ResponseEntity.ok(payments);
    }

    // Get balance additions
    @GetMapping("/balance-additions/{userId}")
    public ResponseEntity<List<PaymentResponse>> getBalanceAdditions(@PathVariable Long userId) {
        List<PaymentResponse> payments = paymentService.getUserBalanceAdditions(userId);
        return ResponseEntity.ok(payments);
    }
}
