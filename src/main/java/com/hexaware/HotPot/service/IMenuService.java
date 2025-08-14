/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.MenuDTO;
import com.hexaware.HotPot.entity.Menu;
import com.hexaware.HotPot.entity.enums.AvailabilitySlot;
import com.hexaware.HotPot.entity.enums.DietaryInfo;
import com.hexaware.HotPot.entity.enums.Taste;

public interface IMenuService {
    
    Menu create(MenuDTO menudto);
    Optional<Menu> getById(Long menuId);
    Menu update(MenuDTO menudto);
    void delete(Long menuId);
    List<Menu> getAllMenu(MenuDTO menudto);
   
    List<Menu> getByRestaurant(Long restaurantId);
    List<Menu> searchByName(String name);
    List<Menu> getByCategory(String category);
    List<Menu> getByDietaryInfo(DietaryInfo dietaryInfo);
    List<Menu> getByTaste(Taste taste);
    List<Menu> getByAvailabilitySlot(AvailabilitySlot slot);
    List<Menu> getByPriceBetween(double minPrice, double maxPrice);
    List<Menu> getAvailable();
}
