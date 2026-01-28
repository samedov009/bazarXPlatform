package com.example.bazarxplatform.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    @NotNull(message = "Alıcı ID-si daxil edilməlidir")
    private Long receiverId;

    private Long adId; // Optional - hansı elana görə yazışırlar

    @NotBlank(message = "Mesaj boş ola bilməz")
    @Size(min = 1, max = 1000, message = "Mesaj 1-1000 simvol arası olmalıdır")
    private String text;
}
