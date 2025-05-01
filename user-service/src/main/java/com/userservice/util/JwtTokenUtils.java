package com.userservice.util;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtils {
//
//    private final String SECRET_KEY = "super-secret-jwt-key";
//    private final long JWT_VALIDITY_MS = 5 * 60 * 60 * 1000;
//
//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//            .setSubject(userDetails.getUsername())
//            .setIssuedAt(new Date())
//            .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY_MS))
//            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//            .compact();
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = getUsernameFromToken(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getClaims(token).getSubject();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return getClaims(token).getExpiration().before(new Date());
//    }
//
//    private Claims getClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
}
