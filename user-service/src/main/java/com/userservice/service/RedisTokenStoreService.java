// 📁 service/RedisTokenStoreService.java
package com.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTokenStoreService {

    private final StringRedisTemplate redisTemplate;
    private static final String TOKEN_PREFIX = "JWT_TOKEN:";

//    public void storeToken(String username, String token, long expirationInSeconds) {
//        String key = TOKEN_PREFIX + token;
//        redisTemplate.opsForValue().set(key, username, Duration.ofSeconds(expirationInSeconds));
//    }
    public void storeToken(String username, String token, long durationInMillis) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(token, username, durationInMillis, TimeUnit.MILLISECONDS);
    }

//    public boolean isTokenValid(String token) {
//        String key = TOKEN_PREFIX + token;
//        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
//    }

//    public boolean isTokenValid(String token) {
//        return redisTemplate.hasKey(token); // or similar
//    }

    public boolean isTokenValid(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

//    public void deleteToken(String token) {
//        String key = TOKEN_PREFIX + token;
//        redisTemplate.delete(key);
//    }

    // 📁 RedisTokenStoreService.java
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }

}
