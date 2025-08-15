/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/

package com.hexaware.HotPot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant {
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long restaurantId;

	    @ManyToOne
	    @JoinColumn(name = "owner_user_id")
	    private User owner; // must have role=RESTAURANT

	    private String restaurantName;
	    private String phone;
	    private String location;
	    private boolean active;
	    
	    public Restaurant() {
	    	
	    }
		public Restaurant(Long restaurantId, User owner, String restaurantName, String phone, String location,
				boolean active) {
			super();
			this.restaurantId = restaurantId;
			this.owner = owner;
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
		public User getOwner() {
			return owner;
		}
		public void setOwner(User owner) {
			this.owner = owner;
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
			return "Restaurant [restaurantId=" + restaurantId + ", owner=" + owner + ", restaurantName="
					+ restaurantName + ", phone=" + phone + ", location=" + location + ", active=" + active + "]";
		}
	    
	    

}
