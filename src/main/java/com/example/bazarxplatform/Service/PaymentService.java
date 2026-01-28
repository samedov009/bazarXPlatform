package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Dto.Request.AddBalanceRequest;
import com.example.bazarxplatform.Dto.Request.PromoteAdRequest;
import com.example.bazarxplatform.Dto.Response.PaymentResponse;
import com.example.bazarxplatform.Entity.BaseAd;
import com.example.bazarxplatform.Entity.Card;
import com.example.bazarxplatform.Entity.Payment;
import com.example.bazarxplatform.Entity.User;
import com.example.bazarxplatform.Enums.PaymentStatus;
import com.example.bazarxplatform.Enums.PaymentType;
import com.example.bazarxplatform.Enums.PromotionType;
import com.example.bazarxplatform.Exception.InsufficientBalanceException;
import com.example.bazarxplatform.Mapper.PaymentMapper;
import com.example.bazarxplatform.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final CardService cardService;
    private final AdService adService;
    private final PaymentMapper paymentMapper;

    // Promotion prices
    private static final BigDecimal SEARCH_PROMOTION_PRICE = new BigDecimal("5.00");
    private static final BigDecimal CATEGORY_PROMOTION_PRICE = new BigDecimal("10.00");
    private static final BigDecimal APP_PROMOTION_PRICE = new BigDecimal("15.00");

    // Add balance
    @Transactional
    public PaymentResponse addBalance(AddBalanceRequest request, Long userId) {
        User user = userService.getUserEntityById(userId);
        Card card = cardService.getCardEntityById(request.getCardId());

        // Check card ownership
        if (!card.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu kart sizə aid deyil");
        }

        // Validate CVV
        if (!cardService.validateCVV(request.getCardId(), request.getCvv())) {
            throw new RuntimeException("CVV səhvdir");
        }

        // Add balance
        userService.updateBalance(userId, request.getAmount());

        // Create payment record
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setCard(card);
        payment.setAmount(request.getAmount());
        payment.setPaymentType(PaymentType.BALANCE_ADD);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setDescription("Balansa " + request.getAmount() + " AZN əlavə edildi");

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(savedPayment);
    }

    // Promote ad
    @Transactional
    public PaymentResponse promoteAd(Long adId, PromoteAdRequest request, Long userId) {
        User user = userService.getUserEntityById(userId);
        Card card = cardService.getCardEntityById(request.getCardId());
        BaseAd ad = adService.getAdEntityById(adId);

        // Check ad ownership
        if (!ad.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu elan sizə aid deyil");
        }

        // Check card ownership
        if (!card.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu kart sizə aid deyil");
        }

        // Validate CVV
        if (!cardService.validateCVV(request.getCardId(), request.getCvv())) {
            throw new RuntimeException("CVV səhvdir");
        }

        // Get price and duration based on promotion type
        BigDecimal price;
        int durationDays;
        PaymentType paymentType;
        String description;

        switch (request.getPromotionType()) {
            case SEARCH:
                price = SEARCH_PROMOTION_PRICE;
                durationDays = 3;
                paymentType = PaymentType.SEARCH_PROMOTION;
                description = "Axtarışda önə çıxarma - 3 gün";
                ad.setSearchFeaturedUntil(LocalDateTime.now().plusDays(durationDays));
                break;
            case CATEGORY:
                price = CATEGORY_PROMOTION_PRICE;
                durationDays = 7;
                paymentType = PaymentType.CATEGORY_PROMOTION;
                description = "Kateqoriyada önə çıxarma - 7 gün";
                ad.setCategoryFeaturedUntil(LocalDateTime.now().plusDays(durationDays));
                break;
            case APP:
                price = APP_PROMOTION_PRICE;
                durationDays = 21;
                paymentType = PaymentType.APP_PROMOTION;
                description = "Ümumi appda önə çıxarma - 21 gün";
                ad.setAppFeaturedUntil(LocalDateTime.now().plusDays(durationDays));
                break;
            default:
                throw new RuntimeException("Önə çıxarma növü düzgün deyil");
        }

        // Check balance
        if (!userService.hasSufficientBalance(userId, price)) {
            throw new InsufficientBalanceException("Balansınız kifayət etmir. Lazımi məbləğ: " + price + " AZN");
        }

        // Deduct from balance
        userService.updateBalance(userId, price.negate());

        // Save ad
        adService.getAdEntityById(adId); // This will save the updated featured fields

        // Create payment record
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setCard(card);
        payment.setAmount(price.negate()); // Negative because it's a deduction
        payment.setPaymentType(paymentType);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setDescription(description);

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(savedPayment);
    }

    // Get user's payment history
    public List<PaymentResponse> getUserPaymentHistory(Long userId) {
        return paymentRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get user's balance additions
    public List<PaymentResponse> getUserBalanceAdditions(Long userId) {
        return paymentRepository.findByUserIdAndPaymentType(userId, PaymentType.BALANCE_ADD)
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }
}