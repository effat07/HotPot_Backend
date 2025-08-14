package com.hexaware.HotPot.service;

import com.hexaware.HotPot.dto.NotificationDTO;
import com.hexaware.HotPot.entity.Notification;
import com.hexaware.HotPot.entity.Order;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.NotificationChannel;
import com.hexaware.HotPot.entity.enums.NotificationType;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.NotificationRepository;
import com.hexaware.HotPot.repository.OrderRepository;
import com.hexaware.HotPot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock private NotificationRepository notificationRepository;
    @Mock private UserRepository userRepository;
    @Mock private OrderRepository orderRepository;

    @InjectMocks private NotificationServiceImpl service;

    private NotificationDTO dto;
    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        dto = new NotificationDTO();
        dto.setUserId(10L);
        dto.setOrderId(20L);
        dto.setChannel(NotificationChannel.EMAIL);
        dto.setType(NotificationType.ORDER_PLACED);

        user = new User();
        user.setUserId(10L);

        order = new Order();
        order.setOrderId(20L);
    }

    @Test
    void create_success() {
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(orderRepository.findById(20L)).thenReturn(Optional.of(order));

        ArgumentCaptor<Notification> cap = ArgumentCaptor.forClass(Notification.class);
        when(notificationRepository.save(cap.capture()))
                .thenAnswer(inv -> { Notification n = cap.getValue(); n.setNotificationId(99L); return n; });

        Notification saved = service.create(dto);

        assertNotNull(saved);
        assertEquals(99L, saved.getNotificationId());
        Notification toSave = cap.getValue();
        assertEquals(user, toSave.getUser());
        assertEquals(order, toSave.getOrder());
        assertEquals(NotificationChannel.EMAIL, toSave.getChannel());
        assertEquals(NotificationType.ORDER_PLACED, toSave.getType());
        assertNotNull(toSave.getSentAt(), "sentAt should be defaulted");
    }

    @Test
    void create_userNotFound_throws() {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.create(dto));
        verifyNoInteractions(orderRepository, notificationRepository);
    }

    @Test
    void create_orderNotFound_throws() {
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(orderRepository.findById(20L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> service.create(dto));
        verifyNoInteractions(notificationRepository);
    }

    @Test
    void getById_found() {
        Notification n = new Notification();
        n.setNotificationId(44L);
        when(notificationRepository.findById(44L)).thenReturn(Optional.of(n));

        Optional<Notification> got = service.getById(44L);
        assertTrue(got.isPresent());
        assertEquals(44L, got.get().getNotificationId());
    }

    @Test
    void getById_notFound_returnsEmpty() {
        when(notificationRepository.findById(123L)).thenReturn(Optional.empty());
        assertTrue(service.getById(123L).isEmpty());
    }

    @Test
    void update_relinkAndModify_success() {
        Notification existing = new Notification();
        existing.setNotificationId(55L);
        existing.setUser(user);
        existing.setOrder(order);
        existing.setChannel(NotificationChannel.EMAIL);
        existing.setType(NotificationType.ORDER_PLACED);
        existing.setSentAt(LocalDateTime.now().minusHours(1));

        dto.setNotificationId(55L);
        dto.setChannel(NotificationChannel.EMAIL);
        dto.setType(NotificationType.ORDER_UPDATED);
        dto.setSentAt(LocalDateTime.now());

        when(notificationRepository.findById(55L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user)); // same user OK
        when(orderRepository.findById(20L)).thenReturn(Optional.of(order)); // same order OK
        when(notificationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Notification updated = service.update(dto);
        assertNotNull(updated);
        assertEquals(NotificationType.ORDER_UPDATED, updated.getType());
        assertEquals(NotificationChannel.EMAIL, updated.getChannel());
        assertNotNull(updated.getSentAt());
    }

    @Test
    void update_missingId_throws() {
        assertThrows(IllegalArgumentException.class, () -> service.update(new NotificationDTO()));
    }

    @Test
    void delete_success() {
        when(notificationRepository.existsById(77L)).thenReturn(true);
        service.delete(77L);
        verify(notificationRepository).deleteById(77L);
    }

    @Test
    void delete_notFound_throws() {
        when(notificationRepository.existsById(88L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.delete(88L));
        verify(notificationRepository, never()).deleteById(anyLong());
    }

    @Test
    void getByUser_success() {
        when(userRepository.existsById(10L)).thenReturn(true);
        when(notificationRepository.findByUserUserId(10L)).thenReturn(List.of(new Notification(), new Notification()));
        List<Notification> list = service.getByUser(10L);
        assertEquals(2, list.size());
    }

    @Test
    void getByUser_userMissing_throws() {
        when(userRepository.existsById(10L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> service.getByUser(10L));
    }
}