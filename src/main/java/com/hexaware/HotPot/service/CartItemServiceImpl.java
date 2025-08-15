/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hexaware.HotPot.dto.CartItemDTO;
import com.hexaware.HotPot.entity.CartItem;
import com.hexaware.HotPot.exception.MenuItemNotFoundException;
import com.hexaware.HotPot.repository.CartItemRepository;
import com.hexaware.HotPot.repository.CartRepository;
import com.hexaware.HotPot.repository.MenuRepository;

@Service
public class CartItemServiceImpl implements ICartItemService {

    private static final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private MenuRepository menuRepository;

    @Override
    public CartItem create(CartItemDTO dto) {
        log.info("Creating new CartItem");

        var cart = cartRepository.findById(dto.getCartId())
            .orElseThrow(() -> {
                log.error("Cart not found: {}", dto.getCartId());
                return new RuntimeException("Cart not found: " + dto.getCartId());
            });

        var menu = menuRepository.findById(dto.getMenuId())
            .orElseThrow(() -> {
                log.error("Menu not found: {}", dto.getMenuId());
                return new MenuItemNotFoundException("Menu not found: " + dto.getMenuId());
            });

        int qty = dto.getQuantity() > 0 ? dto.getQuantity() : 1;

        Double incomingPrice = dto.getItemPrice(); 
        double priceToUse = (incomingPrice != null) ? incomingPrice : menu.getPrice();
        if (priceToUse <= 0) {
            throw new IllegalArgumentException("Item price must be greater than 0");
        }

        CartItem ci = new CartItem();
        ci.setCart(cart);
        ci.setMenuItem(menu);
        ci.setQuantity(qty);
        ci.setItemPrice(priceToUse);
        ci.setNotes(dto.getNotes());

        CartItem saved = cartItemRepository.save(ci);
        log.debug("CartItem created with ID {}", saved.getCartItemId());
        return saved;
    }

    @Override
    public Optional<CartItem> getById(Long cartItemId) {
        log.debug("Fetching CartItem by ID {}", cartItemId);
        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
        if (item.isEmpty()) log.error("CartItem not found: {}", cartItemId);
        return item;
    }

    @Override
    public CartItem update(CartItemDTO dto) {
        log.info("Updating CartItem");
        if (dto.getCartItemId() == null) throw new RuntimeException("cartItemId is required for update");

        CartItem existing = cartItemRepository.findById(dto.getCartItemId())
            .orElseThrow(() -> {
                log.error("CartItem not found: {}", dto.getCartItemId());
                return new RuntimeException("CartItem not found: " + dto.getCartItemId());
            });

        if (dto.getCartId() != null &&
            (existing.getCart() == null || !existing.getCart().getCartId().equals(dto.getCartId()))) {
            var cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> {
                    log.error("Cart not found: {}", dto.getCartId());
                    return new RuntimeException("Cart not found: " + dto.getCartId());
                });
            existing.setCart(cart);
        }

        boolean menuChanged = false;
        if (dto.getMenuId() != null &&
            (existing.getMenuItem() == null || !existing.getMenuItem().getMenuId().equals(dto.getMenuId()))) {
            var menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> {
                    log.error("Menu not found: {}", dto.getMenuId());
                    return new MenuItemNotFoundException("Menu not found: " + dto.getMenuId());
                });
            existing.setMenuItem(menu);
            menuChanged = true;
        }

        if (dto.getQuantity() > 0) existing.setQuantity(dto.getQuantity());

        if (dto.getItemPrice() != null) {
            if (dto.getItemPrice() <= 0) {
                throw new IllegalArgumentException("Item price must be greater than 0");
            }
            existing.setItemPrice(dto.getItemPrice());
        } else if (menuChanged) {
            double newMenuPrice = existing.getMenuItem().getPrice();
            if (newMenuPrice <= 0) {
                throw new IllegalStateException("Menu price must be greater than 0");
            }
            existing.setItemPrice(newMenuPrice);
        }

        if (dto.getNotes() != null) existing.setNotes(dto.getNotes());

        CartItem updated = cartItemRepository.save(existing);
        log.debug("CartItem updated with ID {}", updated.getCartItemId());
        return updated;
    }

    @Override
    public String delete(Long cartItemId) {
        log.info("Deleting CartItem {}", cartItemId);
        if (!cartItemRepository.existsById(cartItemId)) {
            log.error("CartItem not found: {}", cartItemId);
            throw new MenuItemNotFoundException("CartItem not found: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
        log.debug("CartItem {} deleted", cartItemId);
        return "Deleted Successfully";
    }

    @Override
    public List<CartItem> getAllCartItem(CartItemDTO ignored) {
        log.debug("Fetching all CartItems");
        return cartItemRepository.findAll();
    }

    @Override
    public List<CartItem> getByCart(Long cartId) {
        log.debug("Fetching CartItems for cart {}", cartId);
        return cartItemRepository.findByCartCartId(cartId);
    }

    @Override
    public Optional<CartItem> getByCartAndMenu(Long cartId, Long menuId) {
        log.debug("Fetching CartItem for cart {} and menu {}", cartId, menuId);
        return cartItemRepository.findByCartCartIdAndMenuItemMenuId(cartId, menuId);
    }
}
