/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.OrderItemDTO;
import com.hexaware.HotPot.entity.OrderItem;

public interface IOrderItemService {

    OrderItem create(OrderItemDTO orderitemdto);
    Optional<OrderItem> getById(Long orderItemId);
    OrderItem update(OrderItemDTO orderitemdto);
    String delete(Long orderItemId);
    List<OrderItem> getAllOrderItem(OrderItemDTO orderitemdto);
  
    List<OrderItem> getByOrder(Long orderId);
    Optional<OrderItem> getByOrderAndMenu(Long orderId, Long menuId);
}
