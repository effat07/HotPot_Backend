/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/

package com.hexaware.HotPot.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private Menu menuItem;

    private int quantity;
    private double itemPrice; // price per unit at add-to-cart time
    private String notes;
    
    public CartItem() {
    	
    }

	public CartItem(Long cartItemId, Cart cart, Menu menuItem, int quantity, double itemPrice, String notes) {
		super();
		this.cartItemId = cartItemId;
		this.cart = cart;
		this.menuItem = menuItem;
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

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Menu getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(Menu menuItem) {
		this.menuItem = menuItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
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
		return "CartItem [cartItemId=" + cartItemId + ", cart=" + cart + ", menuItem=" + menuItem + ", quantity="
				+ quantity + ", itemPrice=" + itemPrice + ", notes=" + notes + "]";
	}
    
}
