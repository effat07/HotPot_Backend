/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDTO {

    private Long cartItemId; 
    
    @jakarta.validation.constraints.NotNull
    private Long cartId;
    
    @jakarta.validation.constraints.NotNull
    private Long menuId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Item price is required")
    @DecimalMin(value = "0.1", message = "Item price must be greater than 0")
    private Double itemPrice;

    @Size(max = 200, message = "Notes cannot exceed 200 characters")
    private String notes; 
}
