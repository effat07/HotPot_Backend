/*
 * Author: Effat Mujawar
 * Date: 15/08/2025
 */
package com.hexaware.HotPot.controller;

import com.hexaware.HotPot.dto.UserDTO;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.security.JwtService;
import com.hexaware.HotPot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired private AuthService authService;
    @Autowired private JwtService jwtService;

    public static class LoginRequest {
        public String emailOrUserName;
        public String password;
        public String getEmailOrUserName() { return emailOrUserName; }
        public String getPassword() { return password; }
    }

    public static class AuthResponse {
        public String token;
        public User user;
        public AuthResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) 
    public User register(@RequestBody UserDTO dto) {
        User u = new User();
        u.setUserName(dto.getUserName());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword()); 
        u.setPhone(dto.getPhone());
        u.setRole(dto.getRole());
        u.setActive(dto.isActive());

        User saved = authService.register(u);

        
        return saved; 
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        User user = authService.login(req.getEmailOrUserName(), req.getPassword());
        if (user == null) {
            // No ResponseEntity; set 401 via exception
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        String subject = user.getEmail() != null ? user.getEmail() : user.getUserName();
        String token = jwtService.generateToken(
            subject,
            Map.of(
                "uid", user.getUserId(),
                "role", user.getRole() != null ? user.getRole().name() : "CUSTOMER",
                "active", user.isActive()
            )
        );
        return new AuthResponse(token, user);
    }
}
