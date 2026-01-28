package com.example.bazarxplatform.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponse {

    private Long id;
    private Long userId;
    private AdResponse ad; // Tam elan məlumatı
    private LocalDateTime addedAt;
}
