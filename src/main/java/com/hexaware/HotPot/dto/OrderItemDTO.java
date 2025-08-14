/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}
