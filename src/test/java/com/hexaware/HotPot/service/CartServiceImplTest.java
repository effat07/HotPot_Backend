/*
 * Author: Effat Mujawar
 * Date: 15/08/2025
 */
package com.hexaware.HotPot.service;

import com.hexaware.HotPot.entity.Cart;
import com.hexaware.HotPot.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
        cart.setCartId(1L);
    }

    @Test
    void testCreate_Success() {
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.create(cart);

        assertNotNull(result);
        assertEquals(1L, result.getCartId());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testCreate_NullCart_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cartService.create(null));
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testGetById_Found() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getCartId());
        verify(cartRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Cart> result = cartService.getById(1L);

        assertTrue(result.isEmpty());
        verify(cartRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cartService.getById(null));
        verify(cartRepository, never()).findById(any());
    }

    @Test
    void testUpdate_Success() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.update(cart);

        assertNotNull(result);
        assertEquals(1L, result.getCartId());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testUpdate_NullCart_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cartService.update(null));
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testUpdate_CartNotFound_ThrowsException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartService.update(cart));
        verify(cartRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete_Success() {
        when(cartRepository.existsById(1L)).thenReturn(true);

        String result = cartService.delete(1L);

        assertEquals("Deleted Successfully", result);
        verify(cartRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cartService.delete(null));
        verify(cartRepository, never()).deleteById(any());
    }

    @Test
    void testDelete_CartNotFound_ThrowsException() {
        when(cartRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> cartService.delete(1L));
        verify(cartRepository, never()).deleteById(any());
    }

    @Test
    void testGetByUser_Found() {
        when(cartRepository.findByUserUserId(10L)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.getByUser(10L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getCartId());
        verify(cartRepository, times(1)).findByUserUserId(10L);
    }

    @Test
    void testGetByUser_NotFound() {
        when(cartRepository.findByUserUserId(10L)).thenReturn(Optional.empty());

        Optional<Cart> result = cartService.getByUser(10L);

        assertTrue(result.isEmpty());
        verify(cartRepository, times(1)).findByUserUserId(10L);
    }

    @Test
    void testGetByUser_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cartService.getByUser(null));
        verify(cartRepository, never()).findByUserUserId(any());
    }
}
