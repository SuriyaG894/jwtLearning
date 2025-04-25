package com.suriya.JWTAuthentication.utils;

import com.suriya.JWTAuthentication.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtils {

    private String SECRET= "this is the secret key which can be used as signature";
    private SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username){
//        return Jwts
//                .builder()
//                .setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .setIssuedAt(new Date())
//                .signWith(secretKey)
//                .compact();
        return Jwts
                .builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .issuedAt(new Date())
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();

    }

    public Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String username, Users userDetails, String token) {
        return username.equals(userDetails.getUsername()) && !isTokenExpired(extractClaims(token).getExpiration());
    }

    private boolean isTokenExpired(Date date) {
        return date.before(new Date());
    }
}
