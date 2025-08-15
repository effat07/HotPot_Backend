/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/

package com.hexaware.HotPot.entity;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {
	    @Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long paymentId;

	    @OneToOne
	    @JoinColumn(name = "order_id", unique = true)
	    private Order order; // 1:1 for MVP

	    private double amount;

	    @Enumerated(EnumType.STRING)
	    private PaymentStatus status; // PENDING, SUCCESS, FAILED, REFUNDED


	    private LocalDateTime createdAt;
	    
	    public Payment() {
	    	
	    }

		public Payment(Long paymentId, Order order, double amount, PaymentStatus status, LocalDateTime createdAt) {
			super();
			this.paymentId = paymentId;
			this.order = order;
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

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
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
			return "Payment [paymentId=" + paymentId + ", order=" + order + ", amount=" + amount + ", status=" + status
					+ ", createdAt=" + createdAt + "]";
		}
	    
}
