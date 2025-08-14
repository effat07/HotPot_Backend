/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hexaware.HotPot.dto.OrderDTO;
import com.hexaware.HotPot.entity.Order;
import com.hexaware.HotPot.entity.Restaurant;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.OrderStatus;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.exception.RestaurantNotFoundException;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.OrderRepository;
import com.hexaware.HotPot.repository.RestaurantRepository;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class OrderServiceImpl implements IOrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
 
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RestaurantRepository restaurantRepository;

    @Override
    public Order create(OrderDTO dto) {
        log.info("Creating order for user {} at restaurant {}", dto.getUserId(), dto.getRestaurantId());

        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> {
                log.error("User {} not found", dto.getUserId());
                return new UserNotFoundException("User not found: " + dto.getUserId());
            });

        Restaurant rest = restaurantRepository.findById(dto.getRestaurantId())
            .orElseThrow(() -> {
                log.error("Restaurant {} not found", dto.getRestaurantId());
                return new RestaurantNotFoundException("Restaurant not found: " + dto.getRestaurantId());
            });

        Order o = new Order();
        o.setUser(user);
        o.setRestaurant(rest);
        o.setOrderNumber(dto.getOrderNumber());
        o.setAddress(dto.getAddress());
        o.setSubtotal(dto.getSubtotal() != null ? dto.getSubtotal() : 0.0);
        o.setTaxes(dto.getTaxes() != null ? dto.getTaxes() : 0.0);
        o.setDeliveryFee(dto.getDeliveryFee() != null ? dto.getDeliveryFee() : 0.0);
        o.setGrandTotal(dto.getGrandTotal() != null ? dto.getGrandTotal() : 0.0);
        o.setStatus(dto.getStatus());
        o.setPaymentStatus(dto.getPaymentStatus());
        o.setPlacedAt(dto.getPlacedAt() != null ? dto.getPlacedAt() : LocalDateTime.now());
        o.setUpdatedAt(dto.getUpdatedAt());

        Order saved = orderRepository.save(o);
        log.debug("Order created with ID {}", saved.getOrderId());
        return saved;
    }

    @Override
    public Optional<Order> getById(Long orderId) {
        log.debug("Fetching order {}", orderId);
        return orderRepository.findById(orderId);
    }

    @Override
    public Order update(OrderDTO dto) {
        if (dto.getOrderId() == null) {
            log.error("Update failed: orderId is null");
            throw new OrderNotFoundException("orderId is required for update");
        }

        log.info("Updating order {}", dto.getOrderId());

        Order existing = orderRepository.findById(dto.getOrderId())
            .orElseThrow(() -> {
                log.error("Order {} not found", dto.getOrderId());
                return new OrderNotFoundException("Order not found: " + dto.getOrderId());
            });

        if (dto.getUserId() != null &&
            (existing.getUser() == null || !existing.getUser().getUserId().equals(dto.getUserId()))) {
            User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> {
                    log.error("User {} not found", dto.getUserId());
                    return new UserNotFoundException("User not found: " + dto.getUserId());
                });
            existing.setUser(user);
        }

        if (dto.getRestaurantId() != null &&
            (existing.getRestaurant() == null || !existing.getRestaurant().getRestaurantId().equals(dto.getRestaurantId()))) {
            Restaurant rest = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> {
                    log.error("Restaurant {} not found", dto.getRestaurantId());
                    return new RestaurantNotFoundException("Restaurant not found: " + dto.getRestaurantId());
                });
            existing.setRestaurant(rest);
        }

        if (dto.getOrderNumber() != null) existing.setOrderNumber(dto.getOrderNumber());
        if (dto.getAddress() != null)      existing.setAddress(dto.getAddress());
        if (dto.getSubtotal() != null)     existing.setSubtotal(dto.getSubtotal());
        if (dto.getTaxes() != null)        existing.setTaxes(dto.getTaxes());
        if (dto.getDeliveryFee() != null)  existing.setDeliveryFee(dto.getDeliveryFee());
        if (dto.getGrandTotal() != null)   existing.setGrandTotal(dto.getGrandTotal());
        if (dto.getStatus() != null)       existing.setStatus(dto.getStatus());
        if (dto.getPaymentStatus() != null) existing.setPaymentStatus(dto.getPaymentStatus());

        existing.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());

        Order updated = orderRepository.save(existing);
        log.debug("Order {} updated successfully", updated.getOrderId());
        return updated;
    }

    @Override
    public String delete(Long orderId) {
        log.info("Deleting order {}", orderId);
        if (!orderRepository.existsById(orderId)) {
            log.error("Order {} not found", orderId);
            throw new OrderNotFoundException("Order not found: " + orderId);
        }
        orderRepository.deleteById(orderId);
        log.debug("Order {} deleted", orderId);
        return "Deleted Successfully";
    }

    @Override
    public List<Order> getAllOrder(OrderDTO ignored) {
        log.debug("Fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getByUser(Long userId) {
        log.debug("Fetching orders for user {}", userId);
        return orderRepository.findByUserUserId(userId);
    }

    @Override
    public List<Order> getByRestaurant(Long restaurantId) {
        log.debug("Fetching orders for restaurant {}", restaurantId);
        return orderRepository.findByRestaurantRestaurantId(restaurantId);
    }

    @Override
    public List<Order> getByStatus(OrderStatus status) {
        log.debug("Fetching orders with status {}", status);
        return orderRepository.findByStatus(status);
    }
}
