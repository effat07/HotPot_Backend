/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.Optional;
import com.hexaware.HotPot.entity.Cart;

public interface ICartService {
   
    Cart create(Cart cart);
    Optional<Cart> getById(Long cartId);
    Cart update(Cart cart);
    String delete(Long cartId);
    
   
    Optional<Cart> getByUser(Long userId);
}
