package com.emre.rabbitmq.model;

import lombok.*;


import java.io.Serializable;

/**
 * ÖNEMLİ !!!
 * Mutlaka modeller serileştirilmelidir.
 * Ayrıca paket ismi dahil olmak üzere bu sınıfın aynısı iletilen servistede olmalıdır.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveAuthModel implements Serializable {
    Long authid;
    String username;
    String email;
}
