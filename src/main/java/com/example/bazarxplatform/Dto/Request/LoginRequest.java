package com.example.bazarxplatform.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "İstifadəçi adı daxil edilməlidir")
    private String username;

    @NotBlank(message = "Şifrə daxil edilməlidir")
    private String password;
}