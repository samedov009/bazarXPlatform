package com.example.bazarxplatform.Mapper;

import com.example.bazarxplatform.Dto.Request.SendMessageRequest;
import com.example.bazarxplatform.Dto.Response.MessageResponse;
import com.example.bazarxplatform.Entity.BaseAd;
import com.example.bazarxplatform.Entity.Message;
import com.example.bazarxplatform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    // Request -> Entity
    public Message toEntity(SendMessageRequest request, User sender, User receiver, BaseAd ad) {
        if (request == null) {
            return null;
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setAd(ad);
        message.setText(request.getText());

        return message;
    }

    // Entity -> Response
    public MessageResponse toResponse(Message message) {
        if (message == null) {
            return null;
        }

        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setSenderId(message.getSender().getId());
        response.setSenderUsername(message.getSender().getUsername());
        response.setReceiverId(message.getReceiver().getId());
        response.setReceiverUsername(message.getReceiver().getUsername());

        if (message.getAd() != null) {
            response.setAdId(message.getAd().getId());
            // Create short title from description or price
            String adTitle = message.getAd().getPrice() + " AZN - " +
                    message.getAd().getCity();
            response.setAdTitle(adTitle);
        }

        response.setText(message.getText());
        response.setIsRead(message.getIsRead());
        response.setSentAt(message.getSentAt());

        return response;
    }
}
