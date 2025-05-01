package com.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800) // 30 mins session timeout
public class RedisHttpSessionConfig {
    // Spring Session will automatically create Redis-backed HttpSession
    // Enables Redis-backed HttpSession (for login sessions, etc.)
}

//🔐 RedisHttpSessionConfig = Redis Session Storage
//        java
//Copy
//        Edit
//@EnableRedisHttpSession
//public class RedisHttpSessionConfig { ... }
//Used with spring-session-data-redis
//
//Backs your HttpSession with Redis instead of memory
//
//Useful for horizontal scaling of sessions (e.g., in Docker/k8s)
//
//Enables features like session sharing between instances
//
//Think of this as enabling Spring Session backed by Redis for auth/session management.