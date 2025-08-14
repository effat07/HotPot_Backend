/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.CartItemDTO;
import com.hexaware.HotPot.entity.CartItem;

public interface ICartItemService {
    
    CartItem create(CartItemDTO cartitemdto);
    Optional<CartItem> getById(Long cartItemId);
    CartItem update(CartItemDTO cartitemdto);
    String delete(Long cartItemId);
    List<CartItem> getAllCartItem(CartItemDTO cartitemdto);
    
    List<CartItem> getByCart(Long cartId);
    Optional<CartItem> getByCartAndMenu(Long cartId, Long menuId);
}
