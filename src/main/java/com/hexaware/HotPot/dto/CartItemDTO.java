/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CartItemDTO {

    private Long cartItemId; 
    
    @jakarta.validation.constraints.NotNull
    private Long cartId;
    
    @jakarta.validation.constraints.NotNull
    private Long menuId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @DecimalMin(value = "0.1", message = "Item price must be greater than 0")
    private Double itemPrice;

    @Size(max = 200, message = "Notes cannot exceed 200 characters")
    private String notes; 
    
    public CartItemDTO() {
    	
    }

	public CartItemDTO(Long cartItemId, @NotNull Long cartId, @NotNull Long menuId,
			@Min(value = 1, message = "Quantity must be at least 1") int quantity,
			@NotNull(message = "Item price is required") @DecimalMin(value = "0.1", message = "Item price must be greater than 0") Double itemPrice,
			@Size(max = 200, message = "Notes cannot exceed 200 characters") String notes) {
		super();
		this.cartItemId = cartItemId;
		this.cartId = cartId;
		this.menuId = menuId;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		this.notes = notes;
	}

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "CartItemDTO [cartItemId=" + cartItemId + ", cartId=" + cartId + ", menuId=" + menuId + ", quantity="
				+ quantity + ", itemPrice=" + itemPrice + ", notes=" + notes + "]";
	}
    
}
