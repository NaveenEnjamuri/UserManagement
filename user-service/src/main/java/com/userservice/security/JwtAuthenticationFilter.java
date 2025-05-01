// 📁 security/JwtAuthenticationFilter.java
package com.userservice.security;

import com.userservice.service.JwtUserDetailsService;
//import com.userservice.util.JwtTokenUtil;
import com.userservice.security.JwtTokenUtil;
import com.userservice.service.RedisTokenStoreService;
import lombok.RequiredArgsConstructor;
//import lombok.var;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final RedisTokenStoreService redisTokenStoreService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractJwtFromRequest(request);

        if (token != null && redisTokenStoreService.isTokenValid(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
//            var userDetails = userDetailsService.loadUserByUsername(username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(token, userDetails)) {
//                var authentication = new UsernamePasswordAuthenticationToken(
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


//        if (token != null && jwtTokenUtil.validateToken(token) && redisTokenStoreService.isTokenValid(token)) {
//            String username = jwtTokenUtil.getUsernameFromToken(token);
//
//            var userDetails = userDetailsService.loadUserByUsername(username);
//            var authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities()
//            );
//
//            authentication.setDetails(
//                    new WebAuthenticationDetailsSource().buildDetails(request)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }




        //        String username = jwtTokenUtil.getUsernameFromToken(token);
//
//        if (token != null
//                && jwtTokenUtil.validateToken(token, username)
//                && redisTokenStoreService.isTokenValid(token, username)) {
//
//            var userDetails = userDetailsService.loadUserByUsername(username);
//            var authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities()
//            );
//
//            authentication.setDetails(
//                    new WebAuthenticationDetailsSource().buildDetails(request)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
