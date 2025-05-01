package com.userservice.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final long JWT_VALIDITY_MS = 5 * 60 * 60 * 1000;         // 5 hours
    private final long REFRESH_TOKEN_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000; // 7 days

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY_MS))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_MS))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean validateRefreshToken(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromRefreshToken(String token) {
        return getUsernameFromToken(token);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public long getJwtValidityMs() {
        return JWT_VALIDITY_MS;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String resolveTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}



//// --- ✅ JwtTokenUtil.java ---
//package com.userservice.security;
//
//import io.jsonwebtoken.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//import java.util.function.Function;
//
//@Component
//public class JwtTokenUtil {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    private final long JWT_VALIDITY_MS = 5 * 60 * 60 * 1000;
//    private final long REFRESH_TOKEN_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000;
//
//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY_MS))
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
//
//    public String generateRefreshToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_MS))
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
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
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return getExpirationDateFromToken(token).before(new Date());
//    }
//
//    public long getJwtValidityMs() {
//        return JWT_VALIDITY_MS;
//    }
//
//    public String resolveTokenFromRequest(HttpServletRequest request) {
//        String bearer = request.getHeader("Authorization");
//        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
//    }
//}











//// --- ✅ JWT Utility Class: JwtTokenUtil.java ---
//package com.userservice.security;
//
//import io.jsonwebtoken.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import java.util.Date;
//import java.util.function.Function;
//
//@Component
//public class JwtTokenUtil {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expirationMs}")
//    private Long expiration;
//
//    private final String SECRET_KEY = "super-secret-jwt-key";
//    private final long JWT_VALIDITY_MS = 5 * 60 * 60 * 1000; // 5 hours
//
////    public String generateToken(String username) {
////        return Jwts.builder()
////                .setSubject(username)
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + expiration))
////                .signWith(SignatureAlgorithm.HS512, secret)
////                .compact();
////    }
//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY_MS))
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                .compact();
//    }
//
//    private final long REFRESH_TOKEN_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000; // 7 days
//
//    public String generateRefreshToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000))) // 7 days
//                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_MS))
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                .compact();
//    }
//
//    public boolean validateRefreshToken(String refreshToken) {
//        try {
//            getClaims(refreshToken); // Just validate structure
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String getUsernameFromRefreshToken(String token) {
//        return getClaims(token).getSubject();
//    }
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = getUsernameFromToken(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
////    public String getUsernameFromToken(String token) {
////        return getClaims(token).getSubject();
////    }
//
//
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
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
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
////    private boolean isTokenExpired(String token) {
////        return getClaims(token).getExpiration().before(new Date());
////    }
//
//
//    public boolean validateToken(String token, String username) {
//        final String usernameFromToken = getUsernameFromToken(token);
//        return username.equals(usernameFromToken) && !isTokenExpired(token);
//    }
//
//    public long getJwtValidityMs() {
//        return JWT_VALIDITY_MS;
//    }
////
////    public boolean validateToken(String token, UserDetails userDetails) {
////        String username = getUsernameFromToken(token);
////        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
////    }
////
////    public String getUsernameFromToken(String token) {
////        return getClaims(token).getSubject();
////    }
////
////    private boolean isTokenExpired(String token) {
////        return getClaims(token).getExpiration().before(new Date());
////    }
////
//    private Claims getClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//}
