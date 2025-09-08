/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.HotPot.dto.MenuDTO;
import com.hexaware.HotPot.entity.Menu;
import com.hexaware.HotPot.entity.enums.AvailabilitySlot;
import com.hexaware.HotPot.entity.enums.DietaryInfo;
import com.hexaware.HotPot.entity.enums.Taste;
import com.hexaware.HotPot.service.IMenuService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/menus")
public class MenuRestController {

    @Autowired
    private IMenuService menuService;

    @PostMapping
    public Menu create(@Valid @RequestBody MenuDTO dto) {
        return menuService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<Menu> getById(@PathVariable Long id) {
        return menuService.getById(id);
    }

    @PutMapping
    public Menu update(@Valid @RequestBody MenuDTO dto) {
        return menuService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }

    @GetMapping
    public List<Menu> getAll() {
        return menuService.getAllMenu(new MenuDTO());
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public List<Menu> getByRestaurant(@PathVariable Long restaurantId) {
        return menuService.getByRestaurant(restaurantId);
    }

    @GetMapping("/search")
    public List<Menu> searchByName(@RequestParam String name) {
        return menuService.searchByName(name);
    }

    @GetMapping("/by-category/{category}")
    public List<Menu> getByCategory(@PathVariable String category) {
        return menuService.getByCategory(category);
    }

    @GetMapping("/by-dietary")
    public List<Menu> getByDietary(@RequestParam DietaryInfo dietaryInfo) {
        return menuService.getByDietaryInfo(dietaryInfo);
    }

    @GetMapping("/by-taste")
    public List<Menu> getByTaste(@RequestParam Taste taste) {
        return menuService.getByTaste(taste);
    }

    @GetMapping("/by-availability")
    public List<Menu> getByAvailability(@RequestParam AvailabilitySlot slot) {
        return menuService.getByAvailabilitySlot(slot);
    }

    @GetMapping("/by-price-range")
    public List<Menu> getByPriceBetween(@RequestParam double minPrice,
                                                        @RequestParam double maxPrice) {
        return menuService.getByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/available")
    public List<Menu> getAvailable() {
        return menuService.getAvailable();
    }
    @PostMapping(value = "/with-image", consumes = "multipart/form-data")
    public Menu createWithImage(@RequestPart("menu") @Valid MenuDTO dto, @RequestPart("image") MultipartFile image) throws IOException {
        dto.setImage(image.getBytes());
        return menuService.create(dto);
    }
    @PutMapping(value = "/with-image", consumes = "multipart/form-data")
    public Menu updateWithImage(@RequestPart("menu") @Valid MenuDTO dto, @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            dto.setImage(image.getBytes());
        }
        return menuService.update(dto);
    }
}