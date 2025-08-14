/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hexaware.HotPot.dto.MenuDTO;
import com.hexaware.HotPot.entity.Menu;
import com.hexaware.HotPot.entity.Restaurant;
import com.hexaware.HotPot.entity.enums.AvailabilitySlot;
import com.hexaware.HotPot.entity.enums.DietaryInfo;
import com.hexaware.HotPot.entity.enums.Taste;
import com.hexaware.HotPot.exception.MenuItemNotFoundException;
import com.hexaware.HotPot.exception.RestaurantNotFoundException;
import com.hexaware.HotPot.repository.MenuRepository;
import com.hexaware.HotPot.repository.RestaurantRepository;

@Service
public class MenuServiceImpl implements IMenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override 
    public Menu create(MenuDTO dto) {
        log.info("Creating menu item for restaurant {}", dto.getRestaurantId());
        if (dto == null) throw new IllegalArgumentException("MenuDTO must not be null");

        Restaurant rest = restaurantRepository.findById(dto.getRestaurantId())
            .orElseThrow(() -> {
                log.error("Restaurant {} not found", dto.getRestaurantId());
                return new RestaurantNotFoundException("Restaurant not found: " + dto.getRestaurantId());
            });

        Menu entity = mapDtoToEntity(new Menu(), dto);
        entity.setRestaurant(rest);

        Menu saved = menuRepository.save(entity);
        log.debug("Menu created with ID {}", saved.getMenuId());
        return saved;
    }

    @Override
    public Optional<Menu> getById(Long menuId) {
        log.debug("Fetching menu item {}", menuId);
        if (menuId == null) throw new IllegalArgumentException("menuId must not be null");

        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isEmpty()) log.error("Menu item {} not found", menuId);
        return menu;
    }

    @Override
    public Menu update(MenuDTO dto) {
        log.info("Updating menu item {}", dto.getMenuId());
        if (dto == null) throw new IllegalArgumentException("MenuDTO must not be null");
        if (dto.getMenuId() == null) throw new IllegalArgumentException("menuId is required for update");

        Menu existing = menuRepository.findById(dto.getMenuId())
            .orElseThrow(() -> {
                log.error("Menu item {} not found", dto.getMenuId());
                return new MenuItemNotFoundException("Menu item not found with id: " + dto.getMenuId());
            });

        mapDtoToEntity(existing, dto);

        if (dto.getRestaurantId() != null &&
            (existing.getRestaurant() == null || !existing.getRestaurant().getRestaurantId().equals(dto.getRestaurantId()))) {

            Restaurant rest = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> {
                    log.error("Restaurant {} not found", dto.getRestaurantId());
                    return new RestaurantNotFoundException("Restaurant not found: " + dto.getRestaurantId());
                });
            existing.setRestaurant(rest);
        }

        Menu updated = menuRepository.save(existing);
        log.debug("Menu item {} updated", updated.getMenuId());
        return updated;
    }

    @Override
    public void delete(Long menuId) {
        log.info("Deleting menu item {}", menuId);
        if (menuId == null) throw new IllegalArgumentException("menuId must not be null");
        if (!menuRepository.existsById(menuId)) {
            log.error("Menu item {} not found", menuId);
            throw new MenuItemNotFoundException("Menu item not found with id: " + menuId);
        }
        menuRepository.deleteById(menuId);
        log.debug("Menu item {} deleted", menuId);
    }

    @Override
    public List<Menu> getAllMenu(MenuDTO ignored) {
        log.debug("Fetching all menu items");
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> getByRestaurant(Long restaurantId) {
        log.debug("Fetching menu for restaurant {}", restaurantId);
        if (restaurantId == null) throw new IllegalArgumentException("restaurantId must not be null");
        return menuRepository.findByRestaurantRestaurantId(restaurantId);
    }

    @Override
    public List<Menu> searchByName(String namePart) {
        log.debug("Searching menu by name {}", namePart);
        if (namePart == null || namePart.isBlank()) throw new IllegalArgumentException("namePart must not be blank");
        return menuRepository.findByNameContainingIgnoreCase(namePart);
    }

    @Override
    public List<Menu> getByCategory(String category) {
        log.debug("Fetching menu by category {}", category);
        if (category == null || category.isBlank()) throw new IllegalArgumentException("category must not be blank");
        return menuRepository.findByCategoryIgnoreCase(category);
    }

    @Override
    public List<Menu> getByDietaryInfo(DietaryInfo dietaryInfo) {
        log.debug("Fetching menu by dietary info {}", dietaryInfo);
        if (dietaryInfo == null) throw new IllegalArgumentException("dietaryInfo must not be null");
        return menuRepository.findByDietaryInfo(dietaryInfo);
    }

    @Override
    public List<Menu> getByTaste(Taste taste) {
        log.debug("Fetching menu by taste {}", taste);
        if (taste == null) throw new IllegalArgumentException("taste must not be null");
        return menuRepository.findByTaste(taste);
    }

    @Override
    public List<Menu> getByAvailabilitySlot(AvailabilitySlot slot) {
        log.debug("Fetching menu by availability slot {}", slot);
        if (slot == null) throw new IllegalArgumentException("slot must not be null");
        return menuRepository.findByAvailabilitySlot(slot);
    }

    @Override
    public List<Menu> getByPriceBetween(double minPrice, double maxPrice) {
        log.debug("Fetching menu by price between {} and {}", minPrice, maxPrice);
        if (minPrice > maxPrice) {
            double tmp = minPrice; minPrice = maxPrice; maxPrice = tmp;
        }
        return menuRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Menu> getAvailable() {
        log.debug("Fetching available menu items");
        return menuRepository.findByInStockTrue();
    }

    private Menu mapDtoToEntity(Menu target, MenuDTO dto) {
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setCategory(dto.getCategory());
        target.setDietaryInfo(dto.getDietaryInfo());
        target.setTaste(dto.getTaste());
        target.setAvailabilitySlot(dto.getAvailabilitySlot());
        target.setNutritionCalories(dto.getNutritionCalories());
        target.setNutritionProtein(dto.getNutritionProtein());
        target.setNutritionCarbs(dto.getNutritionCarbs());
        target.setNutritionFat(dto.getNutritionFat());
        target.setPrice(dto.getPrice() != null ? dto.getPrice() : 0.0);
        target.setInStock(dto.isInStock());
        if (dto.getImage() != null) {
            target.setImage(dto.getImage());
        }
        return target;
    }
}
