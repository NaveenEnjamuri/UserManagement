package com.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
//@EnableFeignClients // Only if you're calling other services like notification-service
@EnableAsync         // For async tasks (e.g., email token generation)
//@EnableRedisRepositories(basePackages = "com.userservice.repository") // if you are using RedisRepository
@EnableRedisRepositories
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
  // Testing for any failures to push to github
}
//Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY4...



//Annotation | Required When...
//@EnableFeignClients | If you're calling any external service via Feign
//@EnableAsync | If any service uses @Async (email, logging, etc.)
//@EnableRedisRepositories | If you're using Redis-backed repositories (e.g., for caching tokens, sessions, etc.)