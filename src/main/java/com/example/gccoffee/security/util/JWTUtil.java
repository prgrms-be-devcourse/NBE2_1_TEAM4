package com.example.gccoffee.security.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.DateTimeException;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {
    private static String key   //서명 key
            = "123124124123412312324134354646463463463463";

    //JWT 문자열 생성
    public String createToken(Map<String, Object> valueMap, int hour) {
        SecretKey key =null;
        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }

//        Jwts.builder().issuer("me").subject("bob").audience().and().expiration()

        Date now = new Date();

        return Jwts.builder().header().add("alg","HS256").add("type","JWT")
                .and()
                .issuedAt(now)
                .expiration(
                        new Date(now.getTime() +
                                Duration.ofHours(hour).toMillis()))
                .claims(valueMap)   //저장 데이터
                .signWith(key)      //서명 key
                .compact();
    }

    public Map<String, Object> validateToken(String token) {
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseClaimsJws(token).getPayload();
        log.info("--- claims: " + claims);

        return claims;
    }

}
