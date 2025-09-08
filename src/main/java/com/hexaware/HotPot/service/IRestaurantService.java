/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.RestaurantDTO;
import com.hexaware.HotPot.entity.Restaurant;

public interface IRestaurantService {
    
    Restaurant create(RestaurantDTO restaurantddto);
    Optional<Restaurant> getById(Long restaurantId);
    Restaurant update(RestaurantDTO restaurantddto);
    String delete(Long restaurantId);
    List<Restaurant> getAllRestaurant(RestaurantDTO restaurantddto);
    
    List<Restaurant> findByRestaurantName(String restaurantName);
    List<Restaurant> findByLocation(String location);
    boolean existsByRestaurantName(String restaurantName);
    
    List<Restaurant> getRestaurantsByOwner(Long ownerId);

}
