package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Dto.Request.SendMessageRequest;
import com.example.bazarxplatform.Dto.Response.MessageResponse;
import com.example.bazarxplatform.Entity.BaseAd;
import com.example.bazarxplatform.Entity.Message;
import com.example.bazarxplatform.Entity.User;
import com.example.bazarxplatform.Exception.ResourceNotFoundException;
import com.example.bazarxplatform.Mapper.MessageMapper;
import com.example.bazarxplatform.Repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final AdService adService;
    private final MessageMapper messageMapper;

    // Send message
    @Transactional
    public MessageResponse sendMessage(SendMessageRequest request, Long senderId) {
        User sender = userService.getUserEntityById(senderId);
        User receiver = userService.getUserEntityById(request.getReceiverId());

        // Get ad if provided
        BaseAd ad = null;
        if (request.getAdId() != null) {
            ad = adService.getAdEntityById(request.getAdId());
        }

        Message message = messageMapper.toEntity(request, sender, receiver, ad);
        Message savedMessage = messageRepository.save(message);

        return messageMapper.toResponse(savedMessage);
    }

    // Get conversation between two users
    public List<MessageResponse> getConversation(Long userId1, Long userId2) {
        return messageRepository.findConversation(userId1, userId2)
                .stream()
                .map(messageMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get user's received messages
    public List<MessageResponse> getReceivedMessages(Long userId) {
        User user = userService.getUserEntityById(userId);
        return messageRepository.findByReceiver(user)
                .stream()
                .map(messageMapper::toResponse)
                .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()))
                .collect(Collectors.toList());
    }

    // Get user's sent messages
    public List<MessageResponse> getSentMessages(Long userId) {
        User user = userService.getUserEntityById(userId);
        return messageRepository.findBySender(user)
                .stream()
                .map(messageMapper::toResponse)
                .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()))
                .collect(Collectors.toList());
    }

    // Get all messages for user (sent + received)
    public List<MessageResponse> getAllUserMessages(Long userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId, userId)
                .stream()
                .map(messageMapper::toResponse)
                .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()))
                .collect(Collectors.toList());
    }

    // Get unread messages
    public List<MessageResponse> getUnreadMessages(Long userId) {
        return messageRepository.findByReceiverIdAndIsRead(userId, false)
                .stream()
                .map(messageMapper::toResponse)
                .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()))
                .collect(Collectors.toList());
    }

    // Get unread count
    public long getUnreadCount(Long userId) {
        return messageRepository.findByReceiverIdAndIsRead(userId, false).size();
    }

    // Mark message as read
    @Transactional
    public void markAsRead(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Mesaj tapılmadı"));

        // Check if user is the receiver
        if (!message.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Bu mesajı yalnız alan şəxs oxumuş kimi işarələyə bilər");
        }

        message.setIsRead(true);
        messageRepository.save(message);
    }

    // Mark all messages from a sender as read
    @Transactional
    public void markConversationAsRead(Long receiverId, Long senderId) {
        List<Message> messages = messageRepository.findConversation(receiverId, senderId);

        messages.stream()
                .filter(msg -> msg.getReceiver().getId().equals(receiverId) && !msg.getIsRead())
                .forEach(msg -> {
                    msg.setIsRead(true);
                    messageRepository.save(msg);
                });
    }

    // Delete message
    @Transactional
    public void deleteMessage(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Mesaj tapılmadı"));

        // User can delete if they are sender or receiver
        if (!message.getSender().getId().equals(userId) &&
                !message.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Bu mesajı silmək üçün icazəniz yoxdur");
        }

        messageRepository.delete(message);
    }

    // Get messages related to an ad
    public List<MessageResponse> getAdMessages(Long adId) {
        return messageRepository.findByAdId(adId)
                .stream()
                .map(messageMapper::toResponse)
                .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()))
                .collect(Collectors.toList());
    }
}
