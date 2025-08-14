/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hexaware.HotPot.entity.Menu;
import com.hexaware.HotPot.entity.enums.AvailabilitySlot;
import com.hexaware.HotPot.entity.enums.DietaryInfo;
import com.hexaware.HotPot.entity.enums.Taste;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantRestaurantId(Long restaurantId);
    List<Menu> findByNameContainingIgnoreCase(String name);
    List<Menu> findByCategoryIgnoreCase(String category);
    List<Menu> findByDietaryInfo(DietaryInfo dietaryInfo);
    List<Menu> findByTaste(Taste taste);
    List<Menu> findByAvailabilitySlot(AvailabilitySlot slot);
    List<Menu> findByPriceBetween(double minPrice, double maxPrice);
    List<Menu> findByInStockTrue();
}
