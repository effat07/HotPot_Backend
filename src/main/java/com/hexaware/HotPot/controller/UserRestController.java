/*
 * Author: Effat Mujawar
 * Date: 15/08/2025
 */
package com.hexaware.HotPot.controller;

import com.hexaware.HotPot.dto.UserDTO;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.Role;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) 
    public User create(@Valid @RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id,  @RequestBody UserDTO dto) {
        dto.setUserId(id); 
        return userService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUser(new UserDTO());
    }

    @GetMapping("/by-email")
    public User getByEmail(@RequestParam String email) {
        return userService.getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @GetMapping("/by-username")
    public User getByUserName(@RequestParam String userName) {
        return userService.getByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + userName));
    }

    @GetMapping("/exists/email")
    public boolean existsByEmail(@RequestParam String email) {
        return userService.existsByEmail(email);
    }

    @GetMapping("/exists/username")
    public boolean existsByUserName(@RequestParam String userName) {
        return userService.existsByUserName(userName);
    }

    @GetMapping("/by-role")
    public List<User> getByRole(@RequestParam Role role) {
        return userService.getByRole(role);
    }
}
