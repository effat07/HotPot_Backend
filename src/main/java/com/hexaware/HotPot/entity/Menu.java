/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/

package com.hexaware.HotPot.entity;

import java.util.Arrays;

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
    
    public Menu() {
    	
    }

	public Menu(Long menuId, String name, String description, String category, DietaryInfo dietaryInfo, Taste taste,
			AvailabilitySlot availabilitySlot, Integer nutritionCalories, Integer nutritionProtein,
			Integer nutritionCarbs, Integer nutritionFat, double price, boolean inStock, byte[] image,
			Restaurant restaurant) {
		super();
		this.menuId = menuId;
		this.name = name;
		this.description = description;
		this.category = category;
		this.dietaryInfo = dietaryInfo;
		this.taste = taste;
		this.availabilitySlot = availabilitySlot;
		this.nutritionCalories = nutritionCalories;
		this.nutritionProtein = nutritionProtein;
		this.nutritionCarbs = nutritionCarbs;
		this.nutritionFat = nutritionFat;
		this.price = price;
		this.inStock = inStock;
		this.image = image;
		this.restaurant = restaurant;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public DietaryInfo getDietaryInfo() {
		return dietaryInfo;
	}

	public void setDietaryInfo(DietaryInfo dietaryInfo) {
		this.dietaryInfo = dietaryInfo;
	}

	public Taste getTaste() {
		return taste;
	}

	public void setTaste(Taste taste) {
		this.taste = taste;
	}

	public AvailabilitySlot getAvailabilitySlot() {
		return availabilitySlot;
	}

	public void setAvailabilitySlot(AvailabilitySlot availabilitySlot) {
		this.availabilitySlot = availabilitySlot;
	}

	public Integer getNutritionCalories() {
		return nutritionCalories;
	}

	public void setNutritionCalories(Integer nutritionCalories) {
		this.nutritionCalories = nutritionCalories;
	}

	public Integer getNutritionProtein() {
		return nutritionProtein;
	}

	public void setNutritionProtein(Integer nutritionProtein) {
		this.nutritionProtein = nutritionProtein;
	}

	public Integer getNutritionCarbs() {
		return nutritionCarbs;
	}

	public void setNutritionCarbs(Integer nutritionCarbs) {
		this.nutritionCarbs = nutritionCarbs;
	}

	public Integer getNutritionFat() {
		return nutritionFat;
	}

	public void setNutritionFat(Integer nutritionFat) {
		this.nutritionFat = nutritionFat;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", dietaryInfo=" + dietaryInfo + ", taste=" + taste + ", availabilitySlot=" + availabilitySlot
				+ ", nutritionCalories=" + nutritionCalories + ", nutritionProtein=" + nutritionProtein
				+ ", nutritionCarbs=" + nutritionCarbs + ", nutritionFat=" + nutritionFat + ", price=" + price
				+ ", inStock=" + inStock + ", image=" + Arrays.toString(image) + ", restaurant=" + restaurant + "]";
	}
    
    
}
