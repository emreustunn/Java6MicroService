package com.emre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Konfigürasyon dosyası olarak springe bildireceğimiz sınıflara ekliyoruz.
 * @Configuration
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ElasticSecurityConfig {

    @Bean
    JwtTokenFilter getTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        /**
         * _csrf -> bunu kapatmak için disable komutu kullanıyoruz.
         * login sayfasında çıkan csrf unique bir key veriyor
         * bu key login sayfasının kendisi tarafından oluşturup oluşturulmadığını anlaması için oluşur
         * rest api işlemlerinde sıkıntı çıkarır bu yüzden bunu kapatıyoruz.
         */
        httpSecurity.csrf().disable();
        /**
         * gelen bütün isteklere oturum açılmış mı kendini doğrulamışmı bir bak.
         * eğer özel adreslere erişimi açmak istiyorsanız bunları belirterek izin vermelisiniz.
         * Match("/{URLS}") için izin ver permitAll demeliyiz.
         *
         * "/v1/**" -> v1'in altına gelen tüm isteklere izin ver demektir.
         *
         * geri kalan hepsini authenticated et. -> <izni var mı kontrol et.
         */
        httpSecurity.authorizeRequests()
                .antMatchers("/mylogin.html","/v1/**").permitAll()
                .anyRequest().authenticated();
        /**
         * Yetkisiz girişlerde kullanıcıların kendilerini doğrulamaları için login formuna yönlendirme yaparız.
         */
//        httpSecurity.formLogin();
        /**
         * eğer kullanıcıya kendi login formunuzu göstermek istiyorsanız o zaman kendi formunuza izin vererek
         * yönlendirme işlemi yapmalısınız.
         */
//        httpSecurity.formLogin().loginPage("/mylogin.html");

        /**
         * Auth service üzerinden kendisinin doğrulayan bir kişinin isteklerinin nasıl işleyeceğini çözmemiz gerekiyor.
         * Kişinin elinde olan token bilgisi okunarak bu kişiye yetkileri dahilinde geçerli olan token üzerinden oturum izni
         * verilecek, böylece kişi her seferinde kendini doğrulamak zorunda kalmayacaktır.
         *
         * Bunu başarmak için öcelikle filtre işleminin öncesine bir işlem adımı sokarak kişiyi doğruluyoruz.
         */
        httpSecurity.addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
