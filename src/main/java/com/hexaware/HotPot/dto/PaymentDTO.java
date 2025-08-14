/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}
