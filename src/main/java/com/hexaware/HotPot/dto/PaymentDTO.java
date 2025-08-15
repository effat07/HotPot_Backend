/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;


public class PaymentDTO {

    private Long paymentId; 
    
    @jakarta.validation.constraints.NotNull
    private Long orderId;

    @DecimalMin(value = "0.1", message = "Amount must be greater than 0")
    private double amount;

    @NotNull(message = "Payment status is required")
    private PaymentStatus status; // PENDING, SUCCESS, FAILED, REFUNDED

    @NotNull(message = "Created date/time is required")
    private LocalDateTime createdAt;
    
    public PaymentDTO() {
    	
    }

	public PaymentDTO(Long paymentId, @NotNull Long orderId,
			@DecimalMin(value = "0.1", message = "Amount must be greater than 0") double amount,
			@NotNull(message = "Payment status is required") PaymentStatus status,
			@NotNull(message = "Created date/time is required") LocalDateTime createdAt) {
		super();
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.amount = amount;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "PaymentDTO [paymentId=" + paymentId + ", orderId=" + orderId + ", amount=" + amount + ", status="
				+ status + ", createdAt=" + createdAt + "]";
	}
	
    
    
}
