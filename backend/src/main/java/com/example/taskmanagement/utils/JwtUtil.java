package com.example.taskmanagement.utils;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.taskmanagement.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.jwt.expiration}")
    private String EXPIRATION_TIME;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(User user) {

        int expirationTime = Integer.parseInt(EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public String extractEmail(String accessToken) {
        final Claims claims = extractAllClaims(accessToken);
        return claims.getSubject();
    }

    private Date extractExpiration(String accessToken) {
        final Claims claims = extractAllClaims(accessToken);
        return claims.getExpiration();
    }

    public Boolean isTokenValid(String accessToken, UserDetails userDetails) {
        final String userEmail = extractEmail(accessToken);
        return (userEmail.equals(userDetails.getUsername())
                && extractExpiration(accessToken).after(new Date()));
    }

}
