/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import jakarta.validation.constraints.NotNull;

public class CartDTO {

    @NotNull(message = "Cart ID is required")
    private Long cartId;
    
    public CartDTO() {
    	
    }

	public CartDTO(@NotNull(message = "Cart ID is required") Long cartId) {
		super();
		this.cartId = cartId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	@Override
	public String toString() {
		return "CartDTO [cartId=" + cartId + "]";
	}
    
}
