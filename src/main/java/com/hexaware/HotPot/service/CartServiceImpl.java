/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hexaware.HotPot.entity.Cart;
import com.hexaware.HotPot.repository.CartRepository;

@Service
public class CartServiceImpl implements ICartService {

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart create(Cart cart) {
        log.info("Creating new cart");
        if (cart == null) throw new IllegalArgumentException("Cart must not be null");

        Cart saved = cartRepository.save(cart);
        log.debug("Cart created with ID {}", saved.getCartId());
        return saved;
    }

    @Override
    public Optional<Cart> getById(Long cartId) {
        log.debug("Fetching cart by ID {}", cartId);
        if (cartId == null) throw new IllegalArgumentException("Cart ID is required");

        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) log.error("Cart not found: {}", cartId);
        return cart;
    }

    @Override
    public Cart update(Cart cart) {
        log.info("Updating cart");
        if (cart == null || cart.getCartId() == null)
            throw new IllegalArgumentException("Cart ID is required for update");

        cartRepository.findById(cart.getCartId())
            .orElseThrow(() -> {
                log.error("Cart not found: {}", cart.getCartId());
                return new RuntimeException("Cart not found: " + cart.getCartId());
            });

        Cart updated = cartRepository.save(cart);
        log.debug("Cart updated with ID {}", updated.getCartId());
        return updated;
    }

    @Override
    public String delete(Long cartId) {
        log.info("Deleting cart {}", cartId);
        if (cartId == null) throw new IllegalArgumentException("Cart ID is required");
        if (!cartRepository.existsById(cartId)) {
            log.error("Cart not found: {}", cartId);
            throw new RuntimeException("Cart not found: " + cartId);
        }

        cartRepository.deleteById(cartId);
        log.debug("Cart {} deleted", cartId);
        return "Deleted Successfully";
    }

    @Override
    public Optional<Cart> getByUser(Long userId) {
        log.debug("Fetching cart for user {}", userId);
        if (userId == null) throw new IllegalArgumentException("User ID is required");

        Optional<Cart> cart = cartRepository.findByUserUserId(userId);
        if (cart.isEmpty()) log.error("Cart not found for user {}", userId);
        return cart;
    }
}
