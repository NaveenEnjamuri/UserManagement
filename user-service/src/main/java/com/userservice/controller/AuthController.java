package com.userservice.controller;

import com.userservice.dto.LoginResponseDTO;
import com.userservice.dto.TokenResponseDTO;
import com.userservice.security.JwtTokenUtil;
import com.userservice.service.IAuthService;
import com.userservice.service.JwtUserDetailsService;
import com.userservice.service.RedisTokenStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = "Authentication Controller", description = "Login, Logout, Password operations")
public class AuthController {

    private final IAuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final RedisTokenStoreService redisTokenStoreService;

    @PostMapping("/login")
    @ApiOperation("Login user")
    public ResponseEntity<TokenResponseDTO> login(@RequestParam String username,
                                                  @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String accessToken = jwtTokenUtil.generateToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(username);

        redisTokenStoreService.storeToken(username, accessToken, jwtTokenUtil.getJwtValidityMs());

        return ResponseEntity.ok(new TokenResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/logout")
    @ApiOperation("Logout user")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = jwtTokenUtil.resolveTokenFromRequest(request);
        if (token == null) return ResponseEntity.badRequest().body("No token found");

        redisTokenStoreService.deleteToken(token);
        return ResponseEntity.ok("Logged out successfully.");
    }
    //    @PostMapping("/logout")
//    @ApiOperation("Logout user")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//        final String header = request.getHeader("Authorization");
//
//        if (header != null && header.startsWith("Bearer ")) {
//            String token = header.substring(7); // Extract token from header
//            redisTokenStoreService.deleteToken(token); // Delete from Redis
//            SecurityContextHolder.clearContext();
//            return ResponseEntity.ok("User logged out successfully and token invalidated");
//        }
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token found");
//    }

    @PostMapping("/change-password")
    @ApiOperation("Change password")
    public ResponseEntity<String> changePassword(@RequestParam String username,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        authService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }
    //    @PostMapping("/change-password")
//    public ResponseEntity<String> changePassword(@RequestParam String username,
//                                                 @RequestParam String oldPassword,
//                                                 @RequestParam String newPassword) {
//        authService.changePassword(username, oldPassword, newPassword);
//        return ResponseEntity.ok("Password changed successfully");
//    }

    @PostMapping("/refresh-token")
    @ApiOperation("Refresh access token using refresh token")
    public ResponseEntity<TokenResponseDTO> refresh(@RequestParam String refreshToken) {
        if (!jwtTokenUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.badRequest().body(null);
        }
        String username = jwtTokenUtil.getUsernameFromRefreshToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtTokenUtil.generateToken(userDetails);
        redisTokenStoreService.storeToken(username, newAccessToken, jwtTokenUtil.getJwtValidityMs());

        return ResponseEntity.ok(new TokenResponseDTO(newAccessToken, refreshToken));
    }
//        @PostMapping("/refresh-token")
//    @ApiOperation("Refresh JWT access token")
//    public ResponseEntity<TokenResponseDTO> refreshAccessToken(@RequestParam String refreshToken) {
//        if (jwtTokenUtil.validateRefreshToken(refreshToken)) {
//            String username = jwtTokenUtil.getUsernameFromRefreshToken(refreshToken);
////        if (jwtTokenUtil.validateToken(refreshToken)) {
////            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            String newAccessToken = jwtTokenUtil.generateToken(userDetails);
//
//            // ✅ Save new token to Redis
//            redisTokenStoreService.storeToken(username, newAccessToken, jwtTokenUtil.getJwtValidityMs());
//
//            return ResponseEntity.ok(new TokenResponseDTO(newAccessToken, refreshToken));
//        } else {
//            return ResponseEntity.status(401).body(new TokenResponseDTO(null, null));
//        }
//    }
}


