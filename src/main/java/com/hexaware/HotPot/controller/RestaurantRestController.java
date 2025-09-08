/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HotPot.dto.RestaurantDTO;
import com.hexaware.HotPot.entity.Restaurant;
import com.hexaware.HotPot.service.IRestaurantService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantRestController {

    @Autowired
    private IRestaurantService restaurantService;

    @PostMapping
    public Restaurant create(@Valid @RequestBody RestaurantDTO dto) {
        return restaurantService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<Restaurant> getById(@PathVariable Long id) {
        return restaurantService.getById(id);
    }

    @PutMapping
    public Restaurant update(@Valid @RequestBody RestaurantDTO dto) {
        return restaurantService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        restaurantService.delete(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAllRestaurant(new RestaurantDTO());
    }

    @GetMapping("/search/by-name")
    public List<Restaurant> findByName(@RequestParam String restaurantName) {
        return restaurantService.findByRestaurantName(restaurantName);
    }

    @GetMapping("/search/by-location")
    public List<Restaurant> findByLocation(@RequestParam String location) {
        return restaurantService.findByLocation(location);
    }

    @GetMapping("/exists")
    public Boolean existsByName(@RequestParam String restaurantName) {
        return restaurantService.existsByRestaurantName(restaurantName);
    }
    private RestaurantDTO convertToDTO(Restaurant restaurant) {
        return new RestaurantDTO(
            restaurant.getRestaurantId(),
            restaurant.getOwner().getUserId(),
            restaurant.getRestaurantName(),
            restaurant.getPhone(),
            restaurant.getLocation(),
            restaurant.isActive()
        );
    }

    @GetMapping("/by-owner/{ownerId}")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByOwner(@PathVariable Long ownerId) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByOwner(ownerId);
        List<RestaurantDTO> dtos = restaurants.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

   

}