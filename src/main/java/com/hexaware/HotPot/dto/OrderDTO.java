/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.OrderStatus;
import com.hexaware.HotPot.entity.enums.PaymentStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrderDTO {

    private Long orderId; 
    
    @jakarta.validation.constraints.NotNull
    private Long userId;
    
    @jakarta.validation.constraints.NotNull
    private Long restaurantId;

    @NotBlank(message = "Order number is mandatory")
    @Size(max = 50, message = "Order number cannot exceed 50 characters")
    private String orderNumber;

    @NotBlank(message = "Address is mandatory")
    private String address; 

    @NotNull(message = "Subtotal is required")
    @DecimalMin(value = "0.1", message = "Subtotal must be greater than 0")
    private Double subtotal;

    @NotNull(message = "Taxes are required")
    @DecimalMin(value = "0.0", message = "Taxes cannot be negative")
    private Double taxes;

    @NotNull(message = "Delivery fee is required")
    @DecimalMin(value = "0.0", message = "Delivery fee cannot be negative")
    private Double deliveryFee;

    @NotNull(message = "Grand total is required")
    @DecimalMin(value = "0.1", message = "Grand total must be greater than 0")
    private Double grandTotal;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Placed date/time is required")
    private LocalDateTime placedAt;

    private LocalDateTime updatedAt;
    
    public OrderDTO() {
    	
    }

	public OrderDTO(Long orderId, @NotNull Long userId, @NotNull Long restaurantId,
			@NotBlank(message = "Order number is mandatory") @Size(max = 50, message = "Order number cannot exceed 50 characters") String orderNumber,
			@NotBlank(message = "Address is mandatory") String address,
			@NotNull(message = "Subtotal is required") @DecimalMin(value = "0.1", message = "Subtotal must be greater than 0") Double subtotal,
			@NotNull(message = "Taxes are required") @DecimalMin(value = "0.0", message = "Taxes cannot be negative") Double taxes,
			@NotNull(message = "Delivery fee is required") @DecimalMin(value = "0.0", message = "Delivery fee cannot be negative") Double deliveryFee,
			@NotNull(message = "Grand total is required") @DecimalMin(value = "0.1", message = "Grand total must be greater than 0") Double grandTotal,
			@NotNull(message = "Order status is required") OrderStatus status,
			@NotNull(message = "Payment status is required") PaymentStatus paymentStatus,
			@NotNull(message = "Placed date/time is required") LocalDateTime placedAt, LocalDateTime updatedAt) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.orderNumber = orderNumber;
		this.address = address;
		this.subtotal = subtotal;
		this.taxes = taxes;
		this.deliveryFee = deliveryFee;
		this.grandTotal = grandTotal;
		this.status = status;
		this.paymentStatus = paymentStatus;
		this.placedAt = placedAt;
		this.updatedAt = updatedAt;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	public Double getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getPlacedAt() {
		return placedAt;
	}

	public void setPlacedAt(LocalDateTime placedAt) {
		this.placedAt = placedAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", userId=" + userId + ", restaurantId=" + restaurantId
				+ ", orderNumber=" + orderNumber + ", address=" + address + ", subtotal=" + subtotal + ", taxes="
				+ taxes + ", deliveryFee=" + deliveryFee + ", grandTotal=" + grandTotal + ", status=" + status
				+ ", paymentStatus=" + paymentStatus + ", placedAt=" + placedAt + ", updatedAt=" + updatedAt + "]";
	}
    
}
