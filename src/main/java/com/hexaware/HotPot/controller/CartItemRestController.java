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

import com.hexaware.HotPot.dto.CartItemDTO;
import com.hexaware.HotPot.entity.CartItem;
import com.hexaware.HotPot.service.ICartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemRestController {

    @Autowired
    private ICartItemService cartItemService;

    @PostMapping
    public CartItem create(@Valid @RequestBody CartItemDTO dto) {
        return cartItemService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<CartItem> getById(@PathVariable Long id) {
        return cartItemService.getById(id);
    }

    @PutMapping
    public CartItem update(@Valid @RequestBody CartItemDTO dto) {
        return cartItemService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartItemService.delete(id);
    }

    @GetMapping
    public List<CartItem> getAll() {
        
        return cartItemService.getAllCartItem(new CartItemDTO());
    }

    @GetMapping("/by-cart/{cartId}")
    public List<CartItem> getByCart(@PathVariable Long cartId) {
        return cartItemService.getByCart(cartId);
    }

    @GetMapping("/by-cart-and-menu")
    public Optional<CartItem> getByCartAndMenu(@RequestParam Long cartId, @RequestParam Long menuId) {
        return cartItemService.getByCartAndMenu(cartId, menuId);
    }
}