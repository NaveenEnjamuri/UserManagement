package com.notificationservice.config;

import com.userservice.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // ✅ Permit access to Swagger and API docs
                .antMatchers(
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
                // ✅ Allow everything else (it's an internal microservice)
                .anyRequest().permitAll();
    }

    // Only needed if security is enforced later
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



//package com.notificationservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//          .csrf().disable()
//          .authorizeRequests()
//            // ✅ Permit public access to Swagger UI
//            .antMatchers(
//                "/v2/api-docs",
//                "/swagger-resources/**",
//                "/swagger-ui.html",
//                "/webjars/**"
//            ).permitAll()
//            // ✅ Permit public access to auth endpoints
//            .antMatchers("/api/users/register", "/api/auth/**").permitAll()
//            // ✅ Allow OPTIONS calls (CORS pre-flight)
//            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//            // 🔐 All other requests require authentication
//            .anyRequest().authenticated()
//          .and()
//            // ✅ Show default login form
//            .formLogin().permitAll()
//          .and()
//            .logout().permitAll();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Strong hash
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}
