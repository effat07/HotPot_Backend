/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import com.hexaware.HotPot.entity.enums.AvailabilitySlot;
import com.hexaware.HotPot.entity.enums.DietaryInfo;
import com.hexaware.HotPot.entity.enums.Taste;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDTO {

    private Long menuId; 
    
    @jakarta.validation.constraints.NotNull
    private Long restaurantId;

    @NotBlank(message = "Menu item name is mandatory")
    @Size(min = 2, max = 100, message = "Menu item name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    private String description;

    @NotBlank(message = "Category is mandatory")
    @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    private String category;

    @NotNull(message = "Dietary info is required")
    private DietaryInfo dietaryInfo; // VEG, NON_VEG

    @NotNull(message = "Taste info is required")
    private Taste taste; // SWEET, SPICY_LIGHT, etc.

    @NotNull(message = "Availability slot is required")
    private AvailabilitySlot availabilitySlot = AvailabilitySlot.ALL_DAY;

    @Min(value = 0, message = "Calories cannot be negative")
    private Integer nutritionCalories;

    @Min(value = 0, message = "Protein cannot be negative")
    private Integer nutritionProtein;

    @Min(value = 0, message = "Carbs cannot be negative")
    private Integer nutritionCarbs;

    @Min(value = 0, message = "Fat cannot be negative")
    private Integer nutritionFat;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private Double price;

    private boolean inStock = true;

//    @NotNull(message = "Image is required")
    private byte[] image; 
}
