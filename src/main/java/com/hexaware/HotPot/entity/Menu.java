/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/

package com.hexaware.HotPot.entity;

import com.hexaware.HotPot.entity.enums.AvailabilitySlot;
import com.hexaware.HotPot.entity.enums.DietaryInfo;
import com.hexaware.HotPot.entity.enums.Taste;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "menu_items")
public class Menu {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String name;
    private String description;
    private String category;

    @Enumerated(EnumType.STRING)
    private DietaryInfo dietaryInfo; // VEG, NON_VEG

    @Enumerated(EnumType.STRING)
    private Taste taste; // SWEET, SPICY_LIGHT, SPICY_FULL, SAVORY, MILD

    @Enumerated(EnumType.STRING)
    private AvailabilitySlot availabilitySlot = AvailabilitySlot.ALL_DAY;

    // nutrition (numbers)
    private Integer nutritionCalories;
    private Integer nutritionProtein;
    private Integer nutritionCarbs;
    private Integer nutritionFat;

    private double price;
    private boolean inStock = true;

    @Lob
    private byte[] image; 

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
