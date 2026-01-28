package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.Message;
import com.example.bazarxplatform.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);

    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    // İki istifadəçi arasında bütün mesajlar
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :userId1 AND m.receiver.id = :userId2) OR " +
            "(m.sender.id = :userId2 AND m.receiver.id = :userId1) " +
            "ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("userId1") Long userId1,
                                   @Param("userId2") Long userId2);

    // Oxunmamış mesajlar
    List<Message> findByReceiverIdAndIsRead(Long receiverId, Boolean isRead);

    // Elan üzrə mesajlar
    List<Message> findByAdId(Long adId);
}