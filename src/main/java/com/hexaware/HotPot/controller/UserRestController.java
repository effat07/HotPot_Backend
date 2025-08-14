
/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/package com.hexaware.HotPot.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HotPot.dto.UserDTO;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.Role;
import com.hexaware.HotPot.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public User create(@Valid @RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping
    public User update(@Valid @RequestBody UserDTO dto) {
        return userService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
       
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUser(new UserDTO());
    }

    @GetMapping("/by-email")
    public Optional<User> getByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/by-username")
    public Optional<User> getByUserName(@RequestParam String userName) {
        return userService.getByUserName(userName);
    }

    @GetMapping("/exists/email")
    public Boolean existsByEmail(@RequestParam String email) {
        return userService.existsByEmail(email);
    }

    @GetMapping("/exists/username")
    public Boolean existsByUserName(@RequestParam String userName) {
        return userService.existsByUserName(userName);
    }

    @GetMapping("/by-role")
    public List<User> getByRole(@RequestParam Role role) {
        return userService.getByRole(role);
    }
}