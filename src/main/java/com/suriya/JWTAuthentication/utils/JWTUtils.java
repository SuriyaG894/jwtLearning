package com.suriya.JWTAuthentication.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {

    private String SECRET= "this is the secret key which can be used as signature";
    private SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(){
        return Jwts
                .builder()
                .setSubject("suriya")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .setIssuedAt(new Date())
                .signWith(secretKey)
                .compact();
    }
}
