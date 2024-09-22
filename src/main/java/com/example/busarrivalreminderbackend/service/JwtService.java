package com.example.busarrivalreminderbackend.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final SecretKey KEY = Jwts.SIG.HS256.key().build();
    private static final int JWT_EXPIRATION_IN_MS = 1000 * 60 * 60 * 2;

    public String createJwt(Long memberId) {

        Date date = new Date();

        return Jwts.builder()
                .claim("memberId", memberId)
                .expiration(new Date(date.getTime() + JWT_EXPIRATION_IN_MS))
                .signWith(KEY)
                .compact();

    }

}
