package com.hexaware.HotPot.service;

import org.springframework.stereotype.Service;

import com.hexaware.HotPot.entity.User;

@Service
public interface AuthService {
    User register(User user);
    User login(String emailOrUserName, String rawPassword);
}
