/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import java.util.Arrays;

import com.hexaware.HotPot.entity.enums.AvailabilitySlot;
import com.hexaware.HotPot.entity.enums.DietaryInfo;
import com.hexaware.HotPot.entity.enums.Taste;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
    
    public MenuDTO() {
    	
    }

	public MenuDTO(Long menuId, @NotNull Long restaurantId,
			@NotBlank(message = "Menu item name is mandatory") @Size(min = 2, max = 100, message = "Menu item name must be between 2 and 100 characters") String name,
			@NotBlank(message = "Description is mandatory") @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters") String description,
			@NotBlank(message = "Category is mandatory") @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters") String category,
			@NotNull(message = "Dietary info is required") DietaryInfo dietaryInfo,
			@NotNull(message = "Taste info is required") Taste taste,
			@NotNull(message = "Availability slot is required") AvailabilitySlot availabilitySlot,
			@Min(value = 0, message = "Calories cannot be negative") Integer nutritionCalories,
			@Min(value = 0, message = "Protein cannot be negative") Integer nutritionProtein,
			@Min(value = 0, message = "Carbs cannot be negative") Integer nutritionCarbs,
			@Min(value = 0, message = "Fat cannot be negative") Integer nutritionFat,
			@NotNull(message = "Price is required") @DecimalMin(value = "0.1", message = "Price must be greater than 0") Double price,
			boolean inStock, byte[] image) {
		super();
		this.menuId = menuId;
		this.restaurantId = restaurantId;
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
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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

	@Override
	public String toString() {
		return "MenuDTO [menuId=" + menuId + ", restaurantId=" + restaurantId + ", name=" + name + ", description="
				+ description + ", category=" + category + ", dietaryInfo=" + dietaryInfo + ", taste=" + taste
				+ ", availabilitySlot=" + availabilitySlot + ", nutritionCalories=" + nutritionCalories
				+ ", nutritionProtein=" + nutritionProtein + ", nutritionCarbs=" + nutritionCarbs + ", nutritionFat="
				+ nutritionFat + ", price=" + price + ", inStock=" + inStock + ", image=" + Arrays.toString(image)
				+ "]";
	}
    
}
