/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderItemDTO {

    private Long orderItemId; 
    
    @jakarta.validation.constraints.NotNull
    private Long orderId;
    
    @jakarta.validation.constraints.NotNull
    private Long menuId;

    @NotBlank(message = "Item name is mandatory")
    private String itemName; 

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.1", message = "Unit price must be greater than 0")
    private Double unitPrice; // price per unit at order time

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Line total is required")
    @DecimalMin(value = "0.1", message = "Line total must be greater than 0")
    private Double lineTotal; // unitPrice * quantity
    
    public OrderItemDTO() {
    	
    }

	public OrderItemDTO(Long orderItemId, @NotNull Long orderId, @NotNull Long menuId,
			@NotBlank(message = "Item name is mandatory") String itemName,
			@NotNull(message = "Unit price is required") @DecimalMin(value = "0.1", message = "Unit price must be greater than 0") Double unitPrice,
			@Min(value = 1, message = "Quantity must be at least 1") int quantity,
			@NotNull(message = "Line total is required") @DecimalMin(value = "0.1", message = "Line total must be greater than 0") Double lineTotal) {
		super();
		this.orderItemId = orderItemId;
		this.orderId = orderId;
		this.menuId = menuId;
		this.itemName = itemName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.lineTotal = lineTotal;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	@Override
	public String toString() {
		return "OrderItemDTO [orderItemId=" + orderItemId + ", orderId=" + orderId + ", menuId=" + menuId
				+ ", itemName=" + itemName + ", unitPrice=" + unitPrice + ", quantity=" + quantity + ", lineTotal="
				+ lineTotal + "]";
	}
    
    
}
