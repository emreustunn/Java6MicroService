package com.emre.dto.request;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "Kullanıcı adı boş geçilemez.")
    @Size(min = 3, max = 32)
    private String username;
    @Email(message = "Lütfen geçerli bir e-mail adresi giriniz.")
    private String email;
    @NotBlank(message = "Şifre boş geçilemez.")
    @Size(min = 8,max = 64)
    // içinde mutlaka bulunmasını istediğimiz şeyleri koyuyoruz.
    @Pattern( message = "Şifre en az 8 karakter olmalı, içinde en az bir büyük ve bir küçük harf sayı ve rakam olmalıdır.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$")
    private String password;

    private String repassword;
}
