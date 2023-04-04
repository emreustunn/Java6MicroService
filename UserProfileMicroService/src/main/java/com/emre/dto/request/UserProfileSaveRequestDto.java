package com.emre.dto.request;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

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
