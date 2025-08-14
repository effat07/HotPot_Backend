package com.hexaware.HotPot.service;

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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock private MenuRepository menuRepository;
    @Mock private RestaurantRepository restaurantRepository;
    @InjectMocks private MenuServiceImpl menuService;

    private Restaurant rest;
    private MenuDTO dto;
    private Menu entity;

    @BeforeEach
    void setUp() {
        rest = new Restaurant();
        rest.setRestaurantId(7L);

        dto = new MenuDTO();
        dto.setMenuId(null);
        dto.setRestaurantId(7L);
        dto.setName("Veg Burger");
        dto.setDescription("crisp patty with veggies");
        dto.setCategory("FastFood");
        dto.setDietaryInfo(DietaryInfo.VEG);
        dto.setTaste(Taste.SAVORY);
        dto.setAvailabilitySlot(AvailabilitySlot.LUNCH);
        // nutrition are Integers in your entity/service:
        dto.setNutritionCalories(350);
        dto.setNutritionProtein(12);
        dto.setNutritionCarbs(45);
        dto.setNutritionFat(10);
        dto.setPrice(129.0);
        dto.setInStock(true);
        dto.setImage(new byte[]{1,2,3});

        entity = new Menu();
        entity.setMenuId(1L);
        entity.setRestaurant(rest);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setDietaryInfo(dto.getDietaryInfo());
        entity.setTaste(dto.getTaste());
        entity.setAvailabilitySlot(dto.getAvailabilitySlot());
        entity.setNutritionCalories(dto.getNutritionCalories());
        entity.setNutritionProtein(dto.getNutritionProtein());
        entity.setNutritionCarbs(dto.getNutritionCarbs());
        entity.setNutritionFat(dto.getNutritionFat());
        entity.setPrice(dto.getPrice());
        entity.setInStock(dto.isInStock());
        entity.setImage(dto.getImage());
    }

   
    @Test
    void create_linksRestaurant_andSaves() {
        when(restaurantRepository.findById(7L)).thenReturn(Optional.of(rest));
        when(menuRepository.save(any(Menu.class))).thenReturn(entity);

        Menu saved = menuService.create(dto);

        assertNotNull(saved);
        assertEquals(1L, saved.getMenuId());
        ArgumentCaptor<Menu> cap = ArgumentCaptor.forClass(Menu.class);
        verify(menuRepository).save(cap.capture());
        assertEquals(7L, cap.getValue().getRestaurant().getRestaurantId());
        assertEquals("Veg Burger", cap.getValue().getName());
        assertEquals(129.0, cap.getValue().getPrice());
        assertTrue(cap.getValue().isInStock());
        assertNotNull(cap.getValue().getImage()); // byte[]
    }

    @Test
    void create_throwsWhenRestaurantMissing() {
        when(restaurantRepository.findById(7L)).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> menuService.create(dto));
        verify(menuRepository, never()).save(any());
    }

    @Test
    void create_throwsWhenDtoNull() {
        assertThrows(IllegalArgumentException.class, () -> menuService.create(null));
    }

    // -------- READ --------
    @Test
    void getById_found() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(entity));
        assertTrue(menuService.getById(1L).isPresent());
        verify(menuRepository).findById(1L);
    }

    @Test
    void getById_throwsOnNullId() {
        assertThrows(IllegalArgumentException.class, () -> menuService.getById(null));
    }

 
    @Test
    void update_updatesFields_andSaves() {
        MenuDTO upd = new MenuDTO();
        upd.setMenuId(1L);
        upd.setName("Deluxe Veg Burger");
        upd.setPrice(149.0);
        upd.setInStock(false);
        upd.setNutritionCalories(370);
        upd.setImage(new byte[]{9,9}); 

        when(menuRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(menuRepository.save(any(Menu.class))).thenAnswer(i -> i.getArgument(0));

        Menu updated = menuService.update(upd);

        assertEquals("Deluxe Veg Burger", updated.getName());
        assertEquals(149.0, updated.getPrice());
        assertEquals(370, updated.getNutritionCalories());
        assertFalse(updated.isInStock());
        assertArrayEquals(new byte[]{9,9}, updated.getImage());
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void update_relinksRestaurant_whenRestaurantIdChanges() {
        MenuDTO upd = new MenuDTO();
        upd.setMenuId(1L);
        upd.setRestaurantId(99L);

        Restaurant newRest = new Restaurant(); newRest.setRestaurantId(99L);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(restaurantRepository.findById(99L)).thenReturn(Optional.of(newRest));
        when(menuRepository.save(any(Menu.class))).thenAnswer(i -> i.getArgument(0));

        Menu updated = menuService.update(upd);

        assertEquals(99L, updated.getRestaurant().getRestaurantId());
    }

    @Test
    void update_throwsWhenDtoNull_orIdMissing_orNotFound() {
        assertThrows(IllegalArgumentException.class, () -> menuService.update(null));

        MenuDTO noId = new MenuDTO();
        assertThrows(IllegalArgumentException.class, () -> menuService.update(noId));

        MenuDTO bad = new MenuDTO(); bad.setMenuId(777L);
        when(menuRepository.findById(777L)).thenReturn(Optional.empty());
        assertThrows(MenuItemNotFoundException.class, () -> menuService.update(bad));
    }

   
    @Test
    void delete_success() {
        when(menuRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> menuService.delete(1L));
        verify(menuRepository).deleteById(1L);
    }

    @Test
    void delete_throwsWhenNull_orNotExists() {
        assertThrows(IllegalArgumentException.class, () -> menuService.delete(null));
        when(menuRepository.existsById(55L)).thenReturn(false);
        assertThrows(MenuItemNotFoundException.class, () -> menuService.delete(55L));
        verify(menuRepository, never()).deleteById(anyLong());
    }

    
    @Test
    void getAllMenu_returnsList() {
        when(menuRepository.findAll()).thenReturn(List.of(entity));
        assertEquals(1, menuService.getAllMenu(new MenuDTO()).size());
    }

    @Test
    void getByRestaurant_returnsList_andValidatesInput() {
        when(menuRepository.findByRestaurantRestaurantId(7L)).thenReturn(List.of(entity));
        assertEquals(1, menuService.getByRestaurant(7L).size());
        assertThrows(IllegalArgumentException.class, () -> menuService.getByRestaurant(null));
    }

    @Test
    void searchByName_throwsOnBlank_andReturnsList() {
        assertThrows(IllegalArgumentException.class, () -> menuService.searchByName("  "));
        when(menuRepository.findByNameContainingIgnoreCase("burger"))
            .thenReturn(List.of(entity));
        assertEquals(1, menuService.searchByName("burger").size());
    }

    @Test
    void getByCategory_validates_andReturnsList() {
        assertThrows(IllegalArgumentException.class, () -> menuService.getByCategory(" "));
        when(menuRepository.findByCategoryIgnoreCase("FastFood")).thenReturn(List.of(entity));
        assertEquals(1, menuService.getByCategory("FastFood").size());
    }

    @Test
    void getByDietaryInfo_returnsList() {
        when(menuRepository.findByDietaryInfo(DietaryInfo.VEG)).thenReturn(List.of(entity));
        assertEquals(1, menuService.getByDietaryInfo(DietaryInfo.VEG).size());
        assertThrows(IllegalArgumentException.class, () -> menuService.getByDietaryInfo(null));
    }

    @Test
    void getByTaste_returnsList() {
        when(menuRepository.findByTaste(Taste.SWEET)).thenReturn(List.of(entity));
        assertEquals(1, menuService.getByTaste(Taste.SWEET).size());
        assertThrows(IllegalArgumentException.class, () -> menuService.getByTaste(null));
    }

    @Test
    void getByAvailabilitySlot_returnsList() {
        when(menuRepository.findByAvailabilitySlot(AvailabilitySlot.DINNER)).thenReturn(List.of(entity));
        assertEquals(1, menuService.getByAvailabilitySlot(AvailabilitySlot.DINNER).size());
        assertThrows(IllegalArgumentException.class, () -> menuService.getByAvailabilitySlot(null));
    }

    @Test
    void getByPriceBetween_swapsWhenMinGreaterThanMax_andReturnsList() {
        when(menuRepository.findByPriceBetween(100.0, 200.0)).thenReturn(List.of(entity));
        assertEquals(1, menuService.getByPriceBetween(200.0, 100.0).size()); // should swap internally
    }

    @Test
    void getAvailable_returnsList() {
        when(menuRepository.findByInStockTrue()).thenReturn(List.of(entity));
        assertEquals(1, menuService.getAvailable().size());
    }
}