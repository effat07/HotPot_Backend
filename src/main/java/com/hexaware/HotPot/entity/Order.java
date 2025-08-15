/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/

package com.hexaware.HotPot.entity;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.OrderStatus;
import com.hexaware.HotPot.entity.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(unique = true)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // customer

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Lob
    private String address; 

    private double subtotal;
    private double taxes;
    private double deliveryFee;
    private double grandTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PLACED, CONFIRMED, PREPARING, DISPATCHED, DELIVERED, CANCELLED

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PENDING, SUCCESS, FAILED, REFUNDED

    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;
   
    public Order() {
    	
    }

	public Order(Long orderId, String orderNumber, User user, Restaurant restaurant, String address, double subtotal,
			double taxes, double deliveryFee, double grandTotal, OrderStatus status, PaymentStatus paymentStatus,
			LocalDateTime placedAt, LocalDateTime updatedAt) {
		super();
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.user = user;
		this.restaurant = restaurant;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	public double getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
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
		return "Order [orderId=" + orderId + ", orderNumber=" + orderNumber + ", user=" + user + ", restaurant="
				+ restaurant + ", address=" + address + ", subtotal=" + subtotal + ", taxes=" + taxes + ", deliveryFee="
				+ deliveryFee + ", grandTotal=" + grandTotal + ", status=" + status + ", paymentStatus=" + paymentStatus
				+ ", placedAt=" + placedAt + ", updatedAt=" + updatedAt + "]";
	}
    

}
