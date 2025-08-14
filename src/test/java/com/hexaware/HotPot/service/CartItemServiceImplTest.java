package com.hexaware.HotPot.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.HotPot.dto.CartItemDTO;
import com.hexaware.HotPot.entity.CartItem;
import com.hexaware.HotPot.exception.MenuItemNotFoundException;

@SpringBootTest

class CartItemServiceImplTest {

    @Autowired
    private ICartItemService cartItemService;

    private CartItemDTO cartItemDTO;

    @BeforeEach
    void setUp() {
        cartItemDTO = new CartItemDTO();
        cartItemDTO.setQuantity(2);
        cartItemDTO.setItemPrice(12.99);
        cartItemDTO.setNotes("Extra spicy");
    }

    @Test
    void testCreateCartItem_Success() {
        CartItem created = cartItemService.create(cartItemDTO);
        assertNotNull(created.getCartItemId());
        assertEquals(2, created.getQuantity());
        assertEquals(12.99, created.getItemPrice());
        assertEquals("Extra spicy", created.getNotes());
    }

    @Test
    void testGetById_Found() {
        CartItem created = cartItemService.create(cartItemDTO);
        Optional<CartItem> found = cartItemService.getById(created.getCartItemId());
        
        assertTrue(found.isPresent());
        assertEquals(created.getCartItemId(), found.get().getCartItemId());
    }

    @Test
    void testGetById_NotFound() {
        Optional<CartItem> found = cartItemService.getById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testUpdateCartItem_Success() {
        CartItem created = cartItemService.create(cartItemDTO);
        
        CartItemDTO updateDTO = new CartItemDTO();
        updateDTO.setCartItemId(created.getCartItemId());
        updateDTO.setQuantity(3);
        updateDTO.setItemPrice(15.99);
        updateDTO.setNotes("Less spicy");
        
        CartItem updated = cartItemService.update(updateDTO);
        assertEquals(3, updated.getQuantity());
        assertEquals(15.99, updated.getItemPrice());
        assertEquals("Less spicy", updated.getNotes());
    }

    @Test
    void testUpdateCartItem_NotFound() {
        CartItemDTO updateDTO = new CartItemDTO();
        updateDTO.setCartItemId(999L);
        
        assertThrows(MenuItemNotFoundException.class, () -> {
            cartItemService.update(updateDTO);
        });
    }

    @Test
    void testUpdateCartItem_MissingId() {
        CartItemDTO updateDTO = new CartItemDTO();
        updateDTO.setQuantity(1);
        
        assertThrows(MenuItemNotFoundException.class, () -> {
            cartItemService.update(updateDTO);
        });
    }

    @Test
    void testDeleteCartItem_Success() {
        CartItem created = cartItemService.create(cartItemDTO);
        String result = cartItemService.delete(created.getCartItemId());
        
        assertEquals("Deleted Successfully", result);
        assertFalse(cartItemService.getById(created.getCartItemId()).isPresent());
    }

    @Test
    void testDeleteCartItem_NotFound() {
        assertThrows(MenuItemNotFoundException.class, () -> {
            cartItemService.delete(999L);
        });
    }

    @Test
    void testGetAllCartItems() {
        cartItemService.create(cartItemDTO);
        
        CartItemDTO anotherDTO = new CartItemDTO();
        anotherDTO.setQuantity(1);
        anotherDTO.setItemPrice(8.99);
        cartItemService.create(anotherDTO);
        
        List<CartItem> items = cartItemService.getAllCartItem(null);
        assertTrue(items.size() >= 2);
    }

    
}