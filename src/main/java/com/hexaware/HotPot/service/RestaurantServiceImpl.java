/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HotPot.dto.RestaurantDTO;
import com.hexaware.HotPot.entity.Restaurant;
import com.hexaware.HotPot.exception.RestaurantNotFoundException;
import com.hexaware.HotPot.repository.RestaurantRepository;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class RestaurantServiceImpl implements IRestaurantService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant create(RestaurantDTO dto) {
        log.info("Create restaurant called");

        if (dto == null) {
            log.error("RestaurantDTO is null");
            throw new IllegalArgumentException("Restaurant payload is required");
        }

        String name = trimOrNull(dto.getRestaurantName());
        if (name != null && restaurantRepository.existsByRestaurantName(name)) {
            log.error("Restaurant name already exists");
            throw new IllegalArgumentException("Restaurant name already exists: " + name);
        }

        Restaurant r = new Restaurant();
        copyDtoToEntity(dto, r);

        if (dto.getOwnerId() != null) {
            log.debug("Linking owner ID: {}", dto.getOwnerId());
            var owner = userRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found: " + dto.getOwnerId()));
            r.setOwner(owner);
        }

        Restaurant saved = restaurantRepository.save(r);
        log.info("Restaurant created, ID: {}", saved.getRestaurantId());
        return saved;
    }

    @Override
    public Optional<Restaurant> getById(Long restaurantId) {
        log.info("Get restaurant by ID called");
        if (restaurantId == null) throw new IllegalArgumentException("Restaurant ID is required");
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        log.debug("Restaurant found: {}", restaurant.isPresent());
        return restaurant;
    }

    @Override
    public Restaurant update(RestaurantDTO dto) {
        log.info("Update restaurant called");

        if (dto == null || dto.getRestaurantId() == null) {
            log.error("Restaurant ID missing");
            throw new IllegalArgumentException("Restaurant ID is required for update");
        }

        Restaurant existing = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + dto.getRestaurantId()));

        String newName = trimOrNull(dto.getRestaurantName());
        if (newName != null && !newName.equalsIgnoreCase(existing.getRestaurantName())
                && restaurantRepository.existsByRestaurantName(newName)) {
            log.error("Restaurant name already exists");
            throw new IllegalArgumentException("Restaurant name already exists: " + newName);
        }

        copyDtoToEntity(dto, existing);

        if (dto.getOwnerId() != null &&
                (existing.getOwner() == null || !existing.getOwner().getUserId().equals(dto.getOwnerId()))) {
            log.debug("Re-linking owner ID: {}", dto.getOwnerId());
            var owner = userRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found: " + dto.getOwnerId()));
            existing.setOwner(owner);
        }

        Restaurant updated = restaurantRepository.save(existing);
        log.info("Restaurant updated, ID: {}", updated.getRestaurantId());
        return updated;
    }

    @Override
    public String delete(Long restaurantId) {
        log.info("Delete restaurant called");
        if (restaurantId == null) throw new IllegalArgumentException("Restaurant ID is required");

        Restaurant existing = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId));

        restaurantRepository.delete(existing);
        log.info("Restaurant deleted");
        return "Deleted successfully";
    }

    @Override
    public List<Restaurant> getAllRestaurant(RestaurantDTO unused) {
        log.info("Get all restaurants called");
        List<Restaurant> restaurants = restaurantRepository.findAll();
        log.debug("Total restaurants: {}", restaurants.size());
        return restaurants;
    }

    @Override
    public List<Restaurant> findByRestaurantName(String namePart) {
        log.info("Find restaurants by name called");
        String q = namePart == null ? "" : namePart.trim();
        return restaurantRepository.findByRestaurantNameContainingIgnoreCase(q);
    }

    @Override
    public List<Restaurant> findByLocation(String locationPart) {
        log.info("Find restaurants by location called");
        String q = locationPart == null ? "" : locationPart.trim();
        return restaurantRepository.findByLocationContainingIgnoreCase(q);
    }

    @Override
    public boolean existsByRestaurantName(String restaurantName) {
        log.debug("Check restaurant name exists called");
        String name = trimOrNull(restaurantName);
        return name != null && restaurantRepository.existsByRestaurantName(name);
    }

    private void copyDtoToEntity(RestaurantDTO dto, Restaurant e) {
        if (dto.getRestaurantName() != null) e.setRestaurantName(dto.getRestaurantName().trim());
        if (dto.getPhone() != null) e.setPhone(dto.getPhone().trim());
        if (dto.getLocation() != null) e.setLocation(dto.getLocation().trim());
        e.setActive(dto.isActive());
    }

    private String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
