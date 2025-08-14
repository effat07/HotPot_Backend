/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.NotificationDTO;
import com.hexaware.HotPot.entity.Notification;

public interface INotificationService {

    Notification create(NotificationDTO notificationdto);
    Optional<Notification> getById(Long notificationId);
    Notification update(NotificationDTO notificationdto);
    void delete(Long notificationId);
    

    List<Notification> getByUser(Long userId);
}
