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

import com.hexaware.HotPot.dto.OrderItemDTO;
import com.hexaware.HotPot.entity.OrderItem;
import com.hexaware.HotPot.service.IOrderItemService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/order-items")
public class OrderItemRestController {

    @Autowired
    private IOrderItemService orderItemService;

    @PostMapping
    public OrderItem create(@Valid @RequestBody OrderItemDTO dto) {
        return orderItemService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<OrderItem> getById(@PathVariable Long id) {
        return orderItemService.getById(id);
    }

    @PutMapping
    public OrderItem update(@Valid @RequestBody OrderItemDTO dto) {
        return orderItemService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderItemService.delete(id);
    }

    @GetMapping
    public List<OrderItem> getAll() {
        return orderItemService.getAllOrderItem(new OrderItemDTO());
    }

    @GetMapping("/by-order/{orderId}")
    public List<OrderItem> getByOrder(@PathVariable Long orderId) {
        return orderItemService.getByOrder(orderId);
    }

    @GetMapping("/by-order-and-menu")
    public Optional<OrderItem> getByOrderAndMenu(@RequestParam Long orderId,
                                                                 @RequestParam Long menuId) {
        return orderItemService.getByOrderAndMenu(orderId, menuId);
    }
}