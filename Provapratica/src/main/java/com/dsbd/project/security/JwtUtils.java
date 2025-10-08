package com.dsbd.project.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessTokenExpirationMs}")
    private int accessTokenExpirationMs;


    @Value("${jwt.refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;

    public String generateJwtToken(String username) {
        return generateJwtToken(username, null);
    }

    public String generateJwtToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        if (roles != null) {
            claims.put("roles", roles);
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Estrai lo username dal token (dal campo subject)
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRolesFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        Object roles = claims.get("roles");
        if (roles instanceof List) {
            // unchecked cast suppressed intentionally; caller should validate
            return (List<String>) roles;
        }
        return null;
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, refreshTokenExpirationMs);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationMs) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Verifica se il token è valido
    public boolean validateJwtToken(String authToken) {
        try {

            // Esegue il parsing del token mediante la chiave segreta usata in fase di generazione del token
            // Se il sender ha modificato il token, il server non sarà in grado di farne il parsing
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Unable to validate JWT token: {}", e.getMessage());
        }
        return false;
    }
    public Claims decodeRefreshToken(String refreshToken, String secretKey) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(refreshToken);
            System.out.println("Subject: " + claimsJws);
            return claimsJws.getBody();
        } catch (Exception e) {
            // Gestisci eventuali errori di decodifica del token
            e.printStackTrace();
            return null;
        }
    }

}