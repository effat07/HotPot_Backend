/*
 * Author: Effat Mujawar
 * Date: 15/08/2025
 */
package com.hexaware.HotPot.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
  private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

  @Override
  public User register(User user) {
    log.info("Registering new user with email {} and username {}", user.getEmail(), user.getUserName());
    if (user == null) throw new IllegalArgumentException("User must not be null");
    if (isBlank(user.getEmail())) throw new IllegalArgumentException("Email is required");
    if (isBlank(user.getUserName())) throw new IllegalArgumentException("Username is required");
    if (isBlank(user.getPassword())) throw new IllegalArgumentException("Password is required");
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new IllegalArgumentException("Email already in use: " + user.getEmail());
    }
    if (userRepository.existsByUserName(user.getUserName())) {
      throw new IllegalArgumentException("Username already in use: " + user.getUserName());
    }
    String encodedPass = encoder.encode(user.getPassword());
    user.setPassword(encodedPass);
    user.setActive(true); // All new users are active on creation
    User saved = userRepository.save(user);
    log.debug("User registered successfully with ID {}", saved.getUserId());
    return saved;
  }

  @Override
  public User login(String emailOrUserName, String rawPassword) {
    log.info("Login attempt for {}", emailOrUserName);
    if (isBlank(emailOrUserName) || isBlank(rawPassword)) {
      log.warn("Login failed: missing credentials");
      return null;
    }
    Optional<User> byEmail = emailOrUserName.contains("@")
        ? userRepository.findByEmail(emailOrUserName)
        : Optional.empty();
    Optional<User> byUser = byEmail.isEmpty()
        ? userRepository.findByUserName(emailOrUserName)
        : Optional.empty();
    User user = byEmail.or(() -> byUser).orElse(null);
    if (user != null && encoder.matches(rawPassword, user.getPassword())) {
      log.debug("Login successful for userId {}", user.getUserId());
      return user;
    }
    log.warn("Login failed for {}", emailOrUserName);
    return null;
  }

  private boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }
}