/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.NotificationChannel;
import com.hexaware.HotPot.entity.enums.NotificationType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDTO {

    private Long notificationId;
    
    @jakarta.validation.constraints.NotNull
    private Long userId;
    
    @jakarta.validation.constraints.NotNull
    private Long orderId;

    @NotNull(message = "Notification channel is required")
    private NotificationChannel channel; // EMAIL

    @NotNull(message = "Notification type is required")
    private NotificationType type; // ORDER_PLACED, ORDER_UPDATED

    @NotNull(message = "Sent date/time is required")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sentAt;
}
