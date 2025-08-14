/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.OrderDTO;
import com.hexaware.HotPot.entity.Order;
import com.hexaware.HotPot.entity.enums.OrderStatus;

public interface IOrderService {
   
    Order create(OrderDTO orderdto);
    Optional<Order> getById(Long orderId);
    Order update(OrderDTO orderdto);
    String delete(Long orderId);
    List<Order> getAllOrder(OrderDTO orderdto);
    
    List<Order> getByUser(Long userId);
    List<Order> getByRestaurant(Long restaurantId);
    List<Order> getByStatus(OrderStatus status);
   
}
