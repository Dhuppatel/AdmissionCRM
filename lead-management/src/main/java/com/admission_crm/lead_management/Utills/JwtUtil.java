package com.admission_crm.lead_management.Utills;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static String extractUsername(String token) throws Exception {
        // Split JWT into parts (header.payload.signature)
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        // Decode payload (2nd part)
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        // Parse JSON
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> payload = mapper.readValue(payloadJson, Map.class);

        // Most common is "sub", but sometimes "username" or "preferred_username"
        return (String) payload.getOrDefault("sub", payload.get("username"));
    }

//    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private Long expiration;
//
//    private SecretKey getSigningKey() {
//        // Use BASE64 decoding to match your Auth Service implementation
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
////
//
//    @SuppressWarnings("unchecked")
//    public List<String> getRolesFromToken(String token) {
//        try {
//            // Try to get roles as list first
//            List<String> roles = getClaimFromToken(token, claims -> claims.get("roles", List.class));
//            if (roles != null) {
//                return roles;
//            }
//
//            // Fallback: get single role and convert to list
//            Object roleObj = getClaimFromToken(token, claims -> claims.get("role"));
//            if (roleObj != null) {
//                if (roleObj instanceof String) {
//                    return Collections.singletonList((String) roleObj);
//                } else {
//                    // Handle Role object - extract role name
//                    return Collections.singletonList(roleObj.toString());
//                }
//            }
//
//            return Collections.emptyList();
//        } catch (Exception e) {
//            logger.error("Error extracting roles from token: {}", e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        try {
//            return Jwts.parser()
//                    .verifyWith(getSigningKey())
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//            throw e;
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//            throw e;
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//            throw e;
//        } catch (SecurityException e) {
//            logger.error("Invalid JWT signature: {}", e.getMessage());
//            throw e;
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT token compact of handler are invalid: {}", e.getMessage());
//            throw e;
//        }
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    public Boolean validateToken(String token) {
//        try {
//            getAllClaimsFromToken(token);
//            return !isTokenExpired(token);
//        } catch (JwtException | IllegalArgumentException e) {
//            logger.error("JWT validation failed: {}", e.getMessage());
//            return false;
//        }
//    }
//
//    public String extractTokenFromHeader(String authHeader) {
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }
}