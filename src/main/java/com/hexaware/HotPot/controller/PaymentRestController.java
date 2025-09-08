/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HotPot.dto.PaymentDTO;
import com.hexaware.HotPot.entity.Payment;
import com.hexaware.HotPot.service.IPaymentService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public Payment create(@Valid @RequestBody PaymentDTO dto) {
        return paymentService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<Payment> getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @PutMapping
    public Payment update(@Valid @RequestBody PaymentDTO dto) {
        return paymentService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAllPayment(new PaymentDTO());
    }

    @GetMapping("/by-order/{orderId}")
    public Optional<Payment> getByOrder(@PathVariable Long orderId) {
        return paymentService.getByOrder(orderId);
    }

    @GetMapping("/by-user/{userId}")
    public List<Payment> getByUser(@PathVariable Long userId) {
        return paymentService.getByUser(userId);
    }

    @GetMapping("/by-created-between")
    public List<Payment> getByCreatedBetween(
            @RequestParam  LocalDateTime start,
            @RequestParam  LocalDateTime end) {
        return paymentService.getByCreatedBetween(start, end);
    }
}