package com.emre.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenManager {
    //Bütün servicelerimize ekleyeceğimiz bir yapı
    @Value("${authservice.bunu-ben-yazdim.secret-key}")
    String secretKey;
    @Value("${authservice.bunu-ben-yazdim.issuer}")
    String issuer; // oluşturan kişi
    public Optional<String> createToken(Long id){
        String token = null;
        Long exDate = 1000L*60; //geçerlilik zamanı 1000L*15=15sn
        try {
            /**
             * DİKKAT!!!!
             * Claim objelerinin içine önemli ve herkes ile paylaşmayacağınız bilgileri koymazsınız.
             * email, username, password, v.s. gibi önemli bilgiler payload içinde olmaz.
             */
            token = JWT.create().withAudience()
                    .withClaim("id",id)
                    .withClaim("howtopage","AuthMicroService")
                    .withClaim("lastjoin",System.currentTimeMillis())
                    .withClaim("ders","Java JWT")
                    .withClaim("Ögrenci","Emre ÜSTÜN")
                    .withIssuer(issuer) // jwt'nin sahibi.
                    .withIssuedAt(new Date())// token oluşturulma tarihi.
                    .withExpiresAt(new Date(System.currentTimeMillis()+exDate)) //bitiş tarihi.
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception exception){
            return Optional.empty();
        }

    }

    public Boolean verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token); // tokenı kontrol et.
            if (decodedJWT ==null)
                return false;
        }catch (Exception exception){
            return false;
        }
        return true;
    }

    public Optional<Long> getByIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token); // tokenı kontrol et.
            if (decodedJWT ==null)
                return Optional.empty();
            Long id = decodedJWT.getClaim("id").asLong();
//            String howToPage = decodedJWT.getClaim("howtopage"  ).asString();
//            System.out.println("how to page..: " + howToPage);
            return Optional.of(id);
        }catch (Exception exception){
            return Optional.empty();
        }

    }
}
