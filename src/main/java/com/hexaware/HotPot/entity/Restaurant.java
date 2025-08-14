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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

}
