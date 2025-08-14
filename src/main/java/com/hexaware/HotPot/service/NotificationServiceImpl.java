/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hexaware.HotPot.dto.NotificationDTO;
import com.hexaware.HotPot.entity.Notification;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.NotificationRepository;
import com.hexaware.HotPot.repository.OrderRepository;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class NotificationServiceImpl implements INotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Notification create(NotificationDTO dto) {
        log.info("Creating notification for user {} and order {}", dto.getUserId(), dto.getOrderId());

        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> {
                    log.error("User {} not found", dto.getUserId());
                    return new UserNotFoundException("User not found: " + dto.getUserId());
                });

        var order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> {
                    log.error("Order {} not found", dto.getOrderId());
                    return new OrderNotFoundException("Order not found: " + dto.getOrderId());
                });

        Notification n = new Notification();
        n.setUser(user);
        n.setOrder(order);
        n.setChannel(dto.getChannel());
        n.setType(dto.getType());
        n.setSentAt(dto.getSentAt() != null ? dto.getSentAt() : java.time.LocalDateTime.now());

        Notification saved = notificationRepository.save(n);
        log.debug("Notification created with ID {}", saved.getNotificationId());
        return saved;
    }

    @Override
    public Optional<Notification> getById(Long notificationId) {
        log.debug("Fetching notification {}", notificationId);
        Optional<Notification> n = notificationRepository.findById(notificationId);
        if (n.isEmpty()) log.error("Notification {} not found", notificationId);
        return n;
    }

    @Override
    public Notification update(NotificationDTO dto) {
        if (dto.getNotificationId() == null) {
            log.error("Update failed: notificationId is null");
            throw new IllegalArgumentException("notificationId is required for update");
        }

        log.info("Updating notification {}", dto.getNotificationId());

        Notification existing = notificationRepository.findById(dto.getNotificationId())
                .orElseThrow(() -> {
                    log.error("Notification {} not found", dto.getNotificationId());
                    return new IllegalArgumentException("Notification not found: " + dto.getNotificationId());
                });

        if (dto.getUserId() != null &&
            (existing.getUser() == null || !existing.getUser().getUserId().equals(dto.getUserId()))) {
            var user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> {
                        log.error("User {} not found", dto.getUserId());
                        return new UserNotFoundException("User not found: " + dto.getUserId());
                    });
            existing.setUser(user);
        }

        if (dto.getOrderId() != null &&
            (existing.getOrder() == null || !existing.getOrder().getOrderId().equals(dto.getOrderId()))) {
            var order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> {
                        log.error("Order {} not found", dto.getOrderId());
                        return new OrderNotFoundException("Order not found: " + dto.getOrderId());
                    });
            existing.setOrder(order);
        }

        if (dto.getChannel() != null) existing.setChannel(dto.getChannel());
        if (dto.getType() != null) existing.setType(dto.getType());
        if (dto.getSentAt() != null) existing.setSentAt(dto.getSentAt());

        Notification updated = notificationRepository.save(existing);
        log.debug("Notification {} updated", updated.getNotificationId());
        return updated;
    }

    @Override
    public void delete(Long notificationId) {
        log.info("Deleting notification {}", notificationId);
        if (notificationId == null) {
            log.error("Delete failed: notificationId is null");
            throw new IllegalArgumentException("notificationId is required");
        }
        if (!notificationRepository.existsById(notificationId)) {
            log.error("Notification {} not found", notificationId);
            throw new IllegalArgumentException("Notification not found: " + notificationId);
        }
        notificationRepository.deleteById(notificationId);
        log.debug("Notification {} deleted", notificationId);
    }

    @Override
    public List<Notification> getByUser(Long userId) {
        log.debug("Fetching notifications for user {}", userId);
        if (userId == null) {
            log.error("Fetch failed: userId is null");
            throw new IllegalArgumentException("userId is required");
        }
        if (!userRepository.existsById(userId)) {
            log.error("User {} not found", userId);
            throw new UserNotFoundException("User not found: " + userId);
        }
        List<Notification> list = notificationRepository.findByUserUserId(userId);
        log.info("Total notifications found for user {}: {}", userId, list.size());
        return list;
    }
}
