package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.BaseAd;
import com.example.bazarxplatform.Entity.Favorite;
import com.example.bazarxplatform.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    List<Favorite> findByUserId(Long userId);

    Optional<Favorite> findByUserAndAd(User user, BaseAd ad);

    Optional<Favorite> findByUserIdAndAdId(Long userId, Long adId);

    boolean existsByUserIdAndAdId(Long userId, Long adId);

    void deleteByUserIdAndAdId(Long userId, Long adId);
}
