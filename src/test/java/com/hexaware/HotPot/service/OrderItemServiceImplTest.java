package com.hexaware.HotPot.service;

import com.hexaware.HotPot.dto.OrderItemDTO;
import com.hexaware.HotPot.entity.Menu;
import com.hexaware.HotPot.entity.Order;
import com.hexaware.HotPot.entity.OrderItem;
import com.hexaware.HotPot.exception.MenuItemNotFoundException;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.repository.MenuRepository;
import com.hexaware.HotPot.repository.OrderItemRepository;
import com.hexaware.HotPot.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock private OrderItemRepository orderItemRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private MenuRepository menuRepository;

    @InjectMocks private OrderItemServiceImpl service;

    private OrderItemDTO dto;
    private Order order;
    private Menu menu;

    @BeforeEach
    void setUp() {
        dto = new OrderItemDTO();
        dto.setOrderId(1L);
        dto.setMenuId(2L);
        dto.setQuantity(0); // should default to 1
        dto.setUnitPrice(null); // should default to menu price
        dto.setLineTotal(null); // recompute

        order = new Order();
        order.setOrderId(1L);

        menu = new Menu();
        menu.setMenuId(2L);
        menu.setName("Margherita");
        menu.setPrice(250.0);
    }

    @Test
    void create_success_defaultsApplied() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(menuRepository.findById(2L)).thenReturn(Optional.of(menu));

        ArgumentCaptor<OrderItem> cap = ArgumentCaptor.forClass(OrderItem.class);
        when(orderItemRepository.save(cap.capture())).thenAnswer(inv -> {
            OrderItem x = cap.getValue(); x.setOrderItemId(99L); return x;
        });

        OrderItem saved = service.create(dto);

        assertNotNull(saved);
        assertEquals(99L, saved.getOrderItemId());
        OrderItem toSave = cap.getValue();
        assertEquals(order, toSave.getOrder());
        assertEquals(menu, toSave.getMenuItem());
        assertEquals("Margherita", toSave.getItemName());
        assertEquals(250.0, toSave.getUnitPrice(), 0.001);
        assertEquals(1, toSave.getQuantity()); // defaulted
        assertEquals(250.0, toSave.getLineTotal(), 0.001);
    }

    @Test
    void create_menuMissing_throws() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(menuRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(MenuItemNotFoundException.class, () -> service.create(dto));
        verify(orderItemRepository, never()).save(any());
    }

    @Test
    void create_orderMissing_throws() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> service.create(dto));
    }

    @Test
    void getById_found() {
        OrderItem oi = new OrderItem();
        oi.setOrderItemId(7L);
        when(orderItemRepository.findById(7L)).thenReturn(Optional.of(oi));
        assertTrue(service.getById(7L).isPresent());
    }

    @Test
    void update_recalculateLineTotalWhenMissing() {
        OrderItem existing = new OrderItem();
        existing.setOrderItemId(10L);
        existing.setOrder(order);
        existing.setMenuItem(menu);
        existing.setUnitPrice(200.0);
        existing.setQuantity(2);
        existing.setLineTotal(400.0);

        dto.setOrderItemId(10L);
        dto.setQuantity(3);
        dto.setLineTotal(null); // should recompute -> 200 * 3 = 600

        when(orderItemRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(orderItemRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        OrderItem updated = service.update(dto);

        assertEquals(3, updated.getQuantity());
        assertEquals(600.0, updated.getLineTotal(), 0.001);
    }

    @Test
    void update_missingId_throws() {
        OrderItemDTO bad = new OrderItemDTO();
        assertThrows(OrderNotFoundException.class, () -> service.update(bad));
    }

    @Test
    void delete_success() {
        when(orderItemRepository.existsById(77L)).thenReturn(true);
        String msg = service.delete(77L);
        assertEquals("Deleted Successfully", msg);
        verify(orderItemRepository).deleteById(77L);
    }

    @Test
    void delete_notFound_throwsOrderNotFoundException() {
        when(orderItemRepository.existsById(88L)).thenReturn(false);
        assertThrows(OrderNotFoundException.class, () -> service.delete(88L));
    }

    @Test
    void getAll_and_finders_work() {
        when(orderItemRepository.findAll()).thenReturn(List.of(new OrderItem(), new OrderItem()));
        assertEquals(2, service.getAllOrderItem(new OrderItemDTO()).size());

        when(orderItemRepository.findByOrderOrderId(1L)).thenReturn(List.of(new OrderItem()));
        assertEquals(1, service.getByOrder(1L).size());

        when(orderItemRepository.findByOrderOrderIdAndMenuItemMenuId(1L, 2L)).thenReturn(Optional.of(new OrderItem()));
        assertTrue(service.getByOrderAndMenu(1L, 2L).isPresent());
    }
}