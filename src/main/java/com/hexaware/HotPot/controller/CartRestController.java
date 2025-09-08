/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HotPot.entity.Cart;
import com.hexaware.HotPot.service.ICartService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/carts")
public class CartRestController {

    @Autowired
    private ICartService cartService;

    @PostMapping
    public Cart create(@RequestBody Cart cart) {
        return cartService.create(cart);
    }

    @GetMapping("/{id}")
    public Optional<Cart> getById(@PathVariable("id") Long id) {
        return cartService.getById(id);
    }

    @PutMapping
    public Cart update(@RequestBody Cart cart) {
        return cartService.update(cart);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        cartService.delete(id);
    }

    @GetMapping("/by-user/{userId}")
    public Optional<Cart> getByUser(@PathVariable Long userId) {
        return cartService.getByUser(userId);
    }
}