package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HotPot.dto.OrderItemDTO;
import com.hexaware.HotPot.entity.OrderItem;
import com.hexaware.HotPot.exception.MenuItemNotFoundException;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.repository.MenuRepository;
import com.hexaware.HotPot.repository.OrderItemRepository;
import com.hexaware.HotPot.repository.OrderRepository;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public OrderItem create(OrderItemDTO dto) {
        var order = orderRepository.findById(dto.getOrderId())
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + dto.getOrderId()));
        var menu = menuRepository.findById(dto.getMenuId())
            .orElseThrow(() -> new MenuItemNotFoundException("Menu not found: " + dto.getMenuId()));

        OrderItem oi = new OrderItem();
        oi.setOrder(order);
        oi.setMenuItem(menu);

        double unitPrice = dto.getUnitPrice() != null ? dto.getUnitPrice() : menu.getPrice();
        int qty = dto.getQuantity() > 0 ? dto.getQuantity() : 1;

        oi.setItemName(dto.getItemName() != null ? dto.getItemName() : menu.getName());
        oi.setUnitPrice(unitPrice);
        oi.setQuantity(qty);
        oi.setLineTotal(dto.getLineTotal() != null ? dto.getLineTotal() : unitPrice * qty);

        return orderItemRepository.save(oi);
    }

    @Override
    public Optional<OrderItem> getById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }

    @Override
    public OrderItem update(OrderItemDTO dto) {
        if (dto.getOrderItemId() == null)
            throw new OrderNotFoundException("orderItemId is required for update");

        OrderItem existing = orderItemRepository.findById(dto.getOrderItemId())
            .orElseThrow(() -> new OrderNotFoundException("OrderItem not found: " + dto.getOrderItemId()));

        if (dto.getOrderId() != null &&
            (existing.getOrder() == null || !existing.getOrder().getOrderId().equals(dto.getOrderId()))) {
            var order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + dto.getOrderId()));
            existing.setOrder(order);
        }

        if (dto.getMenuId() != null &&
            (existing.getMenuItem() == null || !existing.getMenuItem().getMenuId().equals(dto.getMenuId()))) {
            var menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new MenuItemNotFoundException("Menu not found: " + dto.getMenuId()));
            existing.setMenuItem(menu);
        }

        if (dto.getItemName() != null) existing.setItemName(dto.getItemName());
        if (dto.getUnitPrice() != null) existing.setUnitPrice(dto.getUnitPrice());
        if (dto.getQuantity() > 0) existing.setQuantity(dto.getQuantity());

        if (dto.getLineTotal() != null) {
            existing.setLineTotal(dto.getLineTotal());
        } else {
            existing.setLineTotal(existing.getUnitPrice() * existing.getQuantity());
        }

        return orderItemRepository.save(existing);
    }

    @Override
    public String delete(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new OrderNotFoundException("OrderItem not found: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
        return "Deleted Successfully";
    }

    @Override
    public List<OrderItem> getAllOrderItem(OrderItemDTO ignored) {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getByOrder(Long orderId) {
        return orderItemRepository.findByOrderOrderId(orderId);
    }

    @Override
    public Optional<OrderItem> getByOrderAndMenu(Long orderId, Long menuId) {
        return orderItemRepository.findByOrderOrderIdAndMenuItemMenuId(orderId, menuId);
    }
}
