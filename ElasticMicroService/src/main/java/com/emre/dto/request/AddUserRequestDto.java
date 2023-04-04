package com.emre.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddUserRequestDto {
    Long id;
    Long authid;
    String username;
    String email;
    String ad;
    String adres;
    String telefon;
    String avatar;
}
