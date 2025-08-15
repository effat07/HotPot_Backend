package com.hexaware.HotPot.service;

import com.hexaware.HotPot.dto.OrderDTO;
import com.hexaware.HotPot.entity.Order;
import com.hexaware.HotPot.entity.Restaurant;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.OrderStatus;
import com.hexaware.HotPot.entity.enums.PaymentStatus;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.exception.RestaurantNotFoundException;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.OrderRepository;
import com.hexaware.HotPot.repository.RestaurantRepository;
import com.hexaware.HotPot.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private UserRepository userRepository;
    @Mock private RestaurantRepository restaurantRepository;

    @InjectMocks private OrderServiceImpl orderService;

    private OrderDTO dto;
    private User user;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);

        restaurant = new Restaurant();
        restaurant.setRestaurantId(2L);

        dto = new OrderDTO();
        dto.setUserId(1L);
        dto.setRestaurantId(2L);
        dto.setOrderNumber("ORD123");
        dto.setAddress("123 Main St");
        dto.setSubtotal(100.0);
        dto.setTaxes(10.0);
        dto.setDeliveryFee(5.0);
        dto.setGrandTotal(115.0);
        dto.setStatus(OrderStatus.PLACED);
        dto.setPaymentStatus(PaymentStatus.SUCCESS);
        dto.setPlacedAt(LocalDateTime.of(2025, 8, 15, 10, 0));
    }

    @Test
    void create_shouldSave_whenValid() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant));

        Order saved = new Order();
        saved.setOrderId(10L);
        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        Order out = orderService.create(dto);

        assertNotNull(out);
        assertEquals(10L, out.getOrderId());

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        Order toSave = captor.getValue();
        assertEquals(user, toSave.getUser());
        assertEquals(restaurant, toSave.getRestaurant());
        assertEquals("ORD123", toSave.getOrderNumber());
        assertEquals(115.0, toSave.getGrandTotal());
    }

    @Test
    void create_shouldThrow_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> orderService.create(dto));
    }

    @Test
    void create_shouldThrow_whenRestaurantNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> orderService.create(dto));
    }

    @Test
    void getById_shouldReturnOptional() {
        Order order = new Order();
        order.setOrderId(5L);
        when(orderRepository.findById(5L)).thenReturn(Optional.of(order));

        Optional<Order> out = orderService.getById(5L);

        assertTrue(out.isPresent());
        assertEquals(5L, out.get().getOrderId());
    }

    @Test
    void update_shouldModify_whenValid() {
        dto.setOrderId(3L);
        Order existing = new Order();
        existing.setOrderId(3L);
        existing.setUser(user);
        existing.setRestaurant(restaurant);

        when(orderRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Order updated = orderService.update(dto);

        assertEquals("ORD123", updated.getOrderNumber());
        assertEquals(OrderStatus.PLACED, updated.getStatus());
        assertEquals(115.0, updated.getGrandTotal());
    }

    
    @Test
    void update_shouldThrow_whenOrderIdMissing() {
        assertThrows(OrderNotFoundException.class, () -> orderService.update(dto));
    }

    @Test
    void update_shouldThrow_whenOrderNotFound() {
        dto.setOrderId(99L);
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.update(dto));
    }

    @Test
    void update_shouldThrow_whenUserNotFound() {
        dto.setOrderId(6L);
        dto.setUserId(15L);

        Order existing = new Order();
        existing.setOrderId(6L);
        when(orderRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(15L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> orderService.update(dto));
    }

    @Test
    void update_shouldThrow_whenRestaurantNotFound() {
        dto.setOrderId(7L);
        dto.setRestaurantId(30L);

        Order existing = new Order();
        existing.setOrderId(7L);
        when(orderRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(restaurantRepository.findById(30L)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> orderService.update(dto));
    }

    @Test
    void delete_shouldRemove_whenExists() {
        when(orderRepository.existsById(8L)).thenReturn(true);

        String msg = orderService.delete(8L);

        assertEquals("Deleted Successfully", msg);
        verify(orderRepository).deleteById(8L);
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        when(orderRepository.existsById(9L)).thenReturn(false);
        assertThrows(OrderNotFoundException.class, () -> orderService.delete(9L));
    }

    @Test
    void getAllOrder_shouldReturnList() {
        when(orderRepository.findAll()).thenReturn(List.of(new Order(), new Order()));
        assertEquals(2, orderService.getAllOrder(new OrderDTO()).size());
    }

    @Test
    void getByUser_shouldReturnList() {
        when(orderRepository.findByUserUserId(1L)).thenReturn(List.of(new Order()));
        assertEquals(1, orderService.getByUser(1L).size());
    }

    @Test
    void getByRestaurant_shouldReturnList() {
        when(orderRepository.findByRestaurantRestaurantId(2L)).thenReturn(List.of(new Order()));
        assertEquals(1, orderService.getByRestaurant(2L).size());
    }

    @Test
    void getByStatus_shouldReturnList() {
        when(orderRepository.findByStatus(OrderStatus.PLACED)).thenReturn(List.of(new Order()));
        assertEquals(1, orderService.getByStatus(OrderStatus.PLACED).size());
    }
}
