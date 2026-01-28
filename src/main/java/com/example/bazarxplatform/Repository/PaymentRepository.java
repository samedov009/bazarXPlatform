package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.Payment;
import com.example.bazarxplatform.Entity.User;
import com.example.bazarxplatform.Enums.PaymentStatus;
import com.example.bazarxplatform.Enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUser(User user);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByUserIdAndPaymentType(Long userId, PaymentType paymentType);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByUserIdOrderByCreatedAtDesc(Long userId);
}
