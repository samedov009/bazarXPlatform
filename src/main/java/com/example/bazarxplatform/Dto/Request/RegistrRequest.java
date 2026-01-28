package com.example.bazarxplatform.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrRequest {

    @NotBlank(message = "İstifadəçi adı daxil edilməlidir")
    @Size(min = 3, max = 50, message = "İstifadəçi adı 3-50 simvol arası olmalıdır")
    private String username;

    @NotBlank(message = "Şifrə daxil edilməlidir")
    @Size(min = 6, message = "Şifrə minimum 6 simvol olmalıdır")
    private String password;

    @NotBlank(message = "Ad daxil edilməlidir")
    private String firstName;

    @NotBlank(message = "Soyad daxil edilməlidir")
    private String lastName;

    @NotBlank(message = "Email daxil edilməlidir")
    @Email(message = "Düzgün email daxil edin")
    private String email;

    @NotBlank(message = "Telefon nömrəsi daxil edilməlidir")
    @Pattern(regexp = "\\+994\\d{9}", message = "Telefon formatı: +994XXXXXXXXX")
    private String phoneNumber;
}