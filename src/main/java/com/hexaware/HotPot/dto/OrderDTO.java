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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}
