package com.emre.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseRequestDto {
    String token;
    /**
     * Her bir istekte göstermek istedeğimiz kayıt adedi.
     */
    Integer pageSize;
    /**
     * Şuan bulunduğumuz, talep ettiğimiz sayfa numarası.
     */
    Integer currentPage;
    /**
     * Hangi alana göre sıralama yapılacaksa o alanın adı.
     */
    String sortParameter;
    /**
     * Sıralamanın yönü a..Z, ASC,DESC
     */
    String direction;
}
