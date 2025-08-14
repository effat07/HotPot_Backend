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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}
