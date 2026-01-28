package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.Card;
import com.example.bazarxplatform.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByUser(User user);

    List<Card> findByUserId(Long userId);

    Optional<Card> findByUserAndIsDefault(User user, Boolean isDefault);

    Optional<Card> findByUserIdAndIsDefault(Long userId, Boolean isDefault);
}
