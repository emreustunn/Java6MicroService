package com.emre.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfileSaveRequestDto {
    Long authid;
    String username;
    String email;
}
