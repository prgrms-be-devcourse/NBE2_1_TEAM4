package edu.example.coffeeproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {
    private static String key =
            "1234567891234567891233456878912312356";
    static int x = 4;

    public String createToken(Map<String, Object> valueMap, int min) {

        SecretKey key = null;
        String x = "3";

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }

        Date now = new Date();

        return Jwts.builder().header().add("alg", "HS256").add("type", "JWT")
                .and()
                .issuedAt(now)
                .expiration(
                        new Date(now.getTime() + Duration.ofMinutes(min).toMillis()))
                .claims(valueMap)
                .signWith(key)
                .compact();
    }

    public Map<String, Object> validateToken(String token) {
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }

        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseClaimsJws(token).getBody();

        log.info("----claim" + claims);
        return claims;
    }
}
