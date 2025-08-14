
/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HotPot.dto.UserDTO;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.Role;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(UserDTO dto) {
        log.info("Create user called");

        if (dto == null) {
            log.error("UserDTO is null");
            throw new IllegalArgumentException("User payload is required");
        }

        String email = trimOrNull(dto.getEmail());
        String userName = trimOrNull(dto.getUserName());
        String phone = trimOrNull(dto.getPhone());

        log.debug("Email: {}, Username: {}", email, userName);

        if (email == null) throw new IllegalArgumentException("Email is required");
        if (userName == null) throw new IllegalArgumentException("Username is required");
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("Email already in use");
        if (userRepository.existsByUserName(userName)) throw new IllegalArgumentException("Username already in use");

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            log.error("Password is empty");
            throw new IllegalArgumentException("Password is required");
        }

        User user = new User();
        user.setEmail(email);
        user.setUserName(userName);
        user.setPhone(phone);
        user.setRole(dto.getRole());
        user.setActive(dto.isActive());
        user.setPassword(dto.getPassword());

        User savedUser = userRepository.save(user);
        log.info("User created, ID: {}", savedUser.getUserId());
        return savedUser;
    }

    @Override
    public Optional<User> getById(Long userId) {
        log.info("Get user by ID called");
        if (userId == null) throw new IllegalArgumentException("User ID is required");
        Optional<User> user = userRepository.findById(userId);
        log.debug("User found: {}", user.isPresent());
        return user;
    }

    @Override
    public User update(UserDTO dto) {
        log.info("Update user called");

        if (dto == null || dto.getUserId() == null) {
            log.error("User ID missing");
            throw new IllegalArgumentException("User ID is required for update");
        }

        User existing = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String newEmail = trimOrNull(dto.getEmail());
        String newUserName = trimOrNull(dto.getUserName());

        log.debug("New email: {}, New username: {}", newEmail, newUserName);

        if (newEmail != null && !newEmail.equalsIgnoreCase(existing.getEmail())
                && userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (newUserName != null && !newUserName.equalsIgnoreCase(existing.getUserName())
                && userRepository.existsByUserName(newUserName)) {
            throw new IllegalArgumentException("Username already in use");
        }

        if (newEmail != null) existing.setEmail(newEmail);
        if (newUserName != null) existing.setUserName(newUserName);
        if (dto.getPhone() != null) existing.setPhone(trimOrNull(dto.getPhone()));
        if (dto.getRole() != null) existing.setRole(dto.getRole());
        existing.setActive(dto.isActive());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(dto.getPassword());
        }

        User updatedUser = userRepository.save(existing);
        log.info("User updated, ID: {}", updatedUser.getUserId());
        return updatedUser;
    }

    @Override
    public String delete(Long userId) {
        log.info("Delete user called");
        if (userId == null) throw new IllegalArgumentException("User ID is required");

        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(existing);
        log.info("User deleted");
        return "Deleted Successfully";
    }

    @Override
    public List<User> getAllUser(UserDTO dto) {
        log.info("Get all users called");
        List<User> users = userRepository.findAll();
        log.debug("Total users: {}", users.size());
        return users;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        log.info("Get user by email called");
        String e = trimOrNull(email);
        if (e == null) return Optional.empty();
        return userRepository.findByEmail(e);
    }

    @Override
    public Optional<User> getByUserName(String userName) {
        log.info("Get user by username called");
        String u = trimOrNull(userName);
        if (u == null) return Optional.empty();
        return userRepository.findByUserName(u);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.debug("Check email exists called");
        String e = trimOrNull(email);
        return e != null && userRepository.existsByEmail(e);
    }

    @Override
    public boolean existsByUserName(String userName) {
        log.debug("Check username exists called");
        String u = trimOrNull(userName);
        return u != null && userRepository.existsByUserName(u);
    }

    @Override
    public List<User> getByRole(Role role) {
        log.info("Get users by role called");
        if (role == null) throw new IllegalArgumentException("Role is required");
        return userRepository.findByRole(role);
    }

    private String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
