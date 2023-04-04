package com.emre.dto.request;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoLoginRequestDto {
    private String username;
    private String password;

}
