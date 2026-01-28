package com.example.bazarxplatform.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private Long id;
    private Long senderId;
    private String senderUsername;
    private Long receiverId;
    private String receiverUsername;
    private Long adId;
    private String adTitle; // Elanın qısa başlığı
    private String text;
    private Boolean isRead;
    private LocalDateTime sentAt;
}