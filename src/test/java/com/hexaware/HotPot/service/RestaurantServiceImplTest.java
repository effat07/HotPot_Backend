package com.hexaware.HotPot.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.HotPot.dto.RestaurantDTO;
import com.hexaware.HotPot.entity.Restaurant;
import com.hexaware.HotPot.exception.RestaurantNotFoundException;

@SpringBootTest

class RestaurantServiceImplTest {

    @Autowired
    private IRestaurantService restaurantService;

    private RestaurantDTO restaurantDTO;

    @BeforeEach
    void setUp() {
        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurantName("Test Restaurant");
        restaurantDTO.setPhone("1234567890");
        restaurantDTO.setLocation("Test Location");
        restaurantDTO.setActive(true);
    }

    @Test
    void testCreateRestaurant_Success() {
        Restaurant created = restaurantService.create(restaurantDTO);
        assertNotNull(created.getRestaurantId());
        assertEquals("Test Restaurant", created.getRestaurantName());
    }

    

    @Test
    void testGetRestaurantById_Found() {
        Restaurant created = restaurantService.create(restaurantDTO);
        Optional<Restaurant> found = restaurantService.getById(created.getRestaurantId());
        
        assertTrue(found.isPresent());
        assertEquals(created.getRestaurantId(), found.get().getRestaurantId());
    }

    @Test
    void testGetRestaurantById_NotFound() {
        Optional<Restaurant> found = restaurantService.getById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testUpdateRestaurant_Success() {
        Restaurant created = restaurantService.create(restaurantDTO);
        
        RestaurantDTO updateDTO = new RestaurantDTO();
        updateDTO.setRestaurantId(created.getRestaurantId());
        updateDTO.setRestaurantName("Updated Name");
        updateDTO.setLocation("New Location");
        
        Restaurant updated = restaurantService.update(updateDTO);
        assertEquals("Updated Name", updated.getRestaurantName());
        assertEquals("New Location", updated.getLocation());
    }

    @Test
    void testUpdateRestaurant_NotFound() {
        RestaurantDTO updateDTO = new RestaurantDTO();
        updateDTO.setRestaurantId(999L); 
        
        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.update(updateDTO);
        });
    }

    

    @Test
    void testDeleteRestaurant_Success() {
        Restaurant created = restaurantService.create(restaurantDTO);
        String result = restaurantService.delete(created.getRestaurantId());
        
        assertEquals("Deleted successfully", result);
        assertFalse(restaurantService.getById(created.getRestaurantId()).isPresent());
    }

    @Test
    void testDeleteRestaurant_NotFound() {
        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.delete(999L);
        });
    }

    @Test
    void testGetAllRestaurants() {
        restaurantService.create(restaurantDTO);
        
        RestaurantDTO anotherDTO = new RestaurantDTO();
        anotherDTO.setRestaurantName("Another Restaurant");
        restaurantService.create(anotherDTO);
        
        List<Restaurant> restaurants = restaurantService.getAllRestaurant(null);
        assertTrue(restaurants.size() >= 2);
    }

    @Test
    void testFindByRestaurantName() {
        restaurantService.create(restaurantDTO);
        
        List<Restaurant> found = restaurantService.findByRestaurantName("test");
        assertFalse(found.isEmpty());
        assertTrue(found.get(0).getRestaurantName().contains("Test"));
    }

    @Test
    void testFindByLocation() {
        restaurantService.create(restaurantDTO);
        
        List<Restaurant> found = restaurantService.findByLocation("location");
        assertFalse(found.isEmpty());
        assertTrue(found.get(0).getLocation().contains("Location"));
    }

    @Test
    void testExistsByRestaurantName() {
        restaurantService.create(restaurantDTO);
        
        assertTrue(restaurantService.existsByRestaurantName("Test Restaurant"));
        assertFalse(restaurantService.existsByRestaurantName("Non-existent Name"));
    }
}