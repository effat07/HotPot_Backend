/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RestaurantDTO {

    private Long restaurantId; 
    
    private Long ownerId;

    @NotBlank(message = "Restaurant name is mandatory")
   
    private String restaurantName;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "Location is mandatory")
   
    private String location;

    private boolean active;
    
    public RestaurantDTO() {
    	
    }

	public RestaurantDTO(Long restaurantId, Long ownerId,
			@NotBlank(message = "Restaurant name is mandatory") String restaurantName,
			@NotBlank(message = "Phone number is mandatory") @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits") String phone,
			@NotBlank(message = "Location is mandatory") String location, boolean active) {
		super();
		this.restaurantId = restaurantId;
		this.ownerId = ownerId;
		this.restaurantName = restaurantName;
		this.phone = phone;
		this.location = location;
		this.active = active;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "RestaurantDTO [restaurantId=" + restaurantId + ", ownerId=" + ownerId + ", restaurantName="
				+ restaurantName + ", phone=" + phone + ", location=" + location + ", active=" + active + "]";
	}
    
}
