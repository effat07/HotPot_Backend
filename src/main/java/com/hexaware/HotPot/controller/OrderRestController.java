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

import com.hexaware.HotPot.dto.OrderDTO;
import com.hexaware.HotPot.entity.Order;
import com.hexaware.HotPot.entity.enums.OrderStatus;
import com.hexaware.HotPot.service.IOrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    public Order create(@Valid @RequestBody OrderDTO dto) {
        return orderService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<Order> getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PutMapping
    public Order update(@Valid @RequestBody OrderDTO dto) {
        return orderService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAllOrder(new OrderDTO());
    }

    @GetMapping("/by-user/{userId}")
    public List<Order> getByUser(@PathVariable Long userId) {
        return orderService.getByUser(userId);
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public List<Order> getByRestaurant(@PathVariable Long restaurantId) {
        return orderService.getByRestaurant(restaurantId);
    }

    @GetMapping("/by-status")
    public List<Order> getByStatus(@RequestParam OrderStatus status) {
        return orderService.getByStatus(status);
    }
}