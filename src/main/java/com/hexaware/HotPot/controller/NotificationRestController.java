/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HotPot.dto.NotificationDTO;
import com.hexaware.HotPot.entity.Notification;
import com.hexaware.HotPot.service.INotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    @Autowired
    private INotificationService notificationService;

    @PostMapping
    public Notification create(@Valid @RequestBody NotificationDTO dto) {
        return notificationService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<Notification> getById(@PathVariable Long id) {
        return notificationService.getById(id);
    }

    @PutMapping
    public Notification update(@Valid @RequestBody NotificationDTO dto) {
        return notificationService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }

    @GetMapping("/by-user/{userId}")
    public List<Notification> getByUser(@PathVariable Long userId) {
        return notificationService.getByUser(userId);
    }
}