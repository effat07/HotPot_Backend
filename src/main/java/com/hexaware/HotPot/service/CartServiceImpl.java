// src/main/java/com/hexaware/HotPot/service/CartServiceImpl.java

package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hexaware.HotPot.entity.Cart;
import com.hexaware.HotPot.repository.CartItemRepository;
import com.hexaware.HotPot.repository.CartRepository;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class CartServiceImpl implements ICartService {
    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;
    
    @Override
    public Cart create(Cart cart) {
        log.info("Creating new cart for user {}", cart.getUser().getUserId());
        if (cart == null || cart.getUser() == null || cart.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Cart must contain a user ID");
        }
        
        var user = userRepository.findById(cart.getUser().getUserId())
            .orElseThrow(() -> new RuntimeException("User not found: " + cart.getUser().getUserId()));
        cart.setUser(user);

        // Ensure we only create a cart if one doesn't exist
        if (cartRepository.findByUserUserId(user.getUserId()).isPresent()) {
            throw new IllegalStateException("A cart already exists for this user.");
        }
        
        Cart saved = cartRepository.save(cart);
        log.debug("Cart created with ID {}", saved.getCartId());
        return saved;
    }
    
    @Override
    public Optional<Cart> getById(Long cartId) {
        log.debug("Fetching cart by ID {}", cartId);
        if (cartId == null) throw new IllegalArgumentException("Cart ID is required");
        return cartRepository.findById(cartId);
    }
    
    @Override
    public Cart update(Cart cart) {
        log.info("Updating cart {}", cart.getCartId());
        if (cart == null || cart.getCartId() == null)
            throw new IllegalArgumentException("Cart ID is required for update");
        
        Cart existing = cartRepository.findById(cart.getCartId())
            .orElseThrow(() -> new RuntimeException("Cart not found: " + cart.getCartId()));

        if (cart.getUser() != null && cart.getUser().getUserId() != null) {
             var user = userRepository.findById(cart.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + cart.getUser().getUserId()));
            existing.setUser(user);
        }
        
        if (cart.getRestaurant() != null && cart.getRestaurant().getRestaurantId() != null) {
             var restaurant = userRepository.findById(cart.getRestaurant().getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found: " + cart.getRestaurant().getRestaurantId()));
            existing.setRestaurant(restaurant);
        }

        Cart updated = cartRepository.save(existing);
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
        
        // Corrected: First delete all cart items associated with the cart
        cartItemRepository.deleteByCartCartId(cartId);
        
        // Then delete the cart itself
        cartRepository.deleteById(cartId);
        
        log.debug("Cart {} deleted", cartId);
        return "Deleted Successfully";
    }
    
    @Override
    public Optional<Cart> getByUser(Long userId) {
        log.debug("Fetching cart for user {}", userId);
        if (userId == null) throw new IllegalArgumentException("User ID is required");
        return cartRepository.findByUserUserId(userId);
    }
}